import models.PointPackage;
import models.Vehicle;
import models.Package;
import java.util.*;

public class GreedySolver {
    private ArrayList<Package> normalPackages;
    private ArrayList<Package> heavyPackages;
    private ArrayList<Package> placedPackages;
    private ArrayList<PointPackage> solution;

    private int truckX;
    private int truckY;
    private int truckZ;
    private int _xp, _yp, _zp;
    private int _lastKnownMaxWidth;
    private int _lastKnownMaxLength;

    public GreedySolver(ArrayList<Package> packages, Vehicle vehicle) {
        normalPackages = new ArrayList<Package>();
        heavyPackages = new ArrayList<Package>();
        placedPackages = new ArrayList<Package>();
        solution = new ArrayList<PointPackage>();
        _xp = 0;
        _yp = 0;
        _zp = 0;

        for (Package aPackage : packages) {
            if (aPackage.weightClass != 2) {
                normalPackages.add(aPackage);
            } else{
                heavyPackages.add(aPackage);
            }
        }
        truckX = vehicle.length;
        truckY = vehicle.width;
        truckZ = vehicle.height;
    }


    public ArrayList<PointPackage> Solve() {
        SortByMaxArea();
        int startSize = normalPackages.size() + heavyPackages.size();

        while (placedPackages.size() < startSize){
            Package p;
            Package nextHeavy = GetNextHeavyPackage();
            Package nextNormal = GetNextNormalPackage();

            if (nextNormal!= null & nextHeavy!=null){

                if (!DoesPackageFitZ(nextHeavy) & !DoesPackageFitZ(nextNormal)
                        | _zp <= FindTallestHeavyPackage().height){
                     p =  nextHeavy;
                }else {
                     p = nextNormal;
                }

            }else {

                if (nextHeavy == null) {
                    p = nextNormal;
                } else {
                    p = nextHeavy;
                }
            }
            
            if (DoesPackageFitZ(p)) {
                AddPackage(p);
                _zp += p.height;
            } else if (DoesPackageFitY(p)) {
                _yp += _lastKnownMaxWidth;
                _zp = 0;
                AddPackage(p);
                _zp = p.height;
                _lastKnownMaxWidth = 0;
            } else if (DoesPackageFitX(p)) {
                _xp += _lastKnownMaxLength;
                _yp = 0;
                _zp = 0;
                AddPackage(p);
                _zp = p.height;
                _lastKnownMaxLength = 0;
            } else {
                System.out.println("Something went wrong!");
                break;
            }

            if (p == nextHeavy){
                this.RemoveHeavyPackage(p);
            }else{
                RemoveNormalPackage(p);
            }

            SetMaxX(p);
            SetMaxY(p);
        }

        return solution;
    }

    private void SetMaxX(Package p) {
        if (p.length > _lastKnownMaxLength) {
            _lastKnownMaxLength = p.length;
        }
    }

    private void SetMaxY(Package p) {
        if (p.width > _lastKnownMaxWidth) {
            _lastKnownMaxWidth = p.width;
        }
    }

    private boolean DoesPackageFitX(Package p) {
        return (_xp + _lastKnownMaxLength + p.length < truckX);
    }

    private boolean DoesPackageFitY(Package p) {
        return (_yp + _lastKnownMaxWidth + p.width < truckY &
                _xp + p.length < truckX);
    }

    private boolean DoesPackageFitZ(Package p) {
        return (_xp + p.length < truckX &
                _yp + p.width < truckY &
                _zp + p.height < truckZ);
    }

    private void SortByMaxArea() {
        //Sort the lists heavy and normal by area
        normalPackages.sort(new Comparator<Package>() {
            @Override
            public int compare(Package o1, Package o2) {
                return (o2.width * o2.length - o1.width * o1.length);
            }
        });
        heavyPackages.sort(new Comparator<Package>() {
            @Override
            public int compare(Package o1, Package o2) {
                return (o2.width * o2.length - o1.width * o1.length);
            }
        });
    }

    private Package GetNextNormalPackage() {
        if (this.normalPackages.listIterator().hasNext()) {
            Package p = this.normalPackages.listIterator().next();
            return p;
        } else {
            return null;
        }
    }

    private void RemoveNormalPackage(Package p){
        this.normalPackages.remove(p);
    }

    private void RemoveHeavyPackage(Package p){
        this.heavyPackages.remove(p);
    }

    private Package GetNextHeavyPackage() {
        if (heavyPackages.listIterator().hasNext()) {
            Package p = heavyPackages.listIterator().next();
            return p;
        } else {
            return null;
        }
    }

    private void AddPackage(Package p){
        var placedPackage = new PointPackage(p.id, _xp, _xp, _xp, _xp, _xp + p.length, _xp + p.length,
                _xp + p.length, _xp + p.length, _yp, _yp, _yp, _yp, _yp + p.width, _yp + p.width,
                _yp + p.width, _yp + p.width, _zp, _zp, _zp, _zp, _zp + p.height, _zp + p.height,
                _zp + p.height, _zp + p.height, p.weightClass, p.orderClass);
        solution.add(placedPackage);
        placedPackages.add(p);
    }

    private Package FindTallestHeavyPackage() {
        if (heavyPackages.iterator().hasNext()) {
            int highestPackageIndex = 0;
            int highestHeight = 0;
            for (int i = 0; i < this.heavyPackages.size(); i++) {
                if (this.heavyPackages.get(i).height > highestHeight) {
                    highestHeight = this.heavyPackages.get(i).height;
                    highestPackageIndex = i;
                }
            }
            return this.heavyPackages.get(highestPackageIndex);
        } else {
            return null;
        }
    }
}