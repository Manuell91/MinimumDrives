import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static java.lang.System.exit;
import static java.lang.System.in;

public class DiskSpace {
    private ArrayList<Integer> used;
    private ArrayList<Integer> total;
    private final int NULL = 0;

    public DiskSpace(ArrayList<Integer> used, ArrayList<Integer> total) {
        this.used = new ArrayList<>(used);
        this.total = new ArrayList<>(total);
    }

    void setUsed(int position, int element) {
        used.set(position, element);
    }

    void setTotal(int position, int element) {
        total.set(position, element);
    }

    public int minDrives(ArrayList<Integer> used, ArrayList<Integer> total) {
        int disksUsed = NULL;
        int greatestTotalSize = NULL;
        int smallestUsedSize = 1001;
        int positionOfLowestUsedSize = NULL;
        int positionOfHighestUsedSize = NULL;
        ArrayList<Integer> listOfGreatestTotals = new ArrayList<>();
        //ArrayList<Integer> listOfPositionsWithLowestUsedSize = new ArrayList<>();
        int availableDiskSpace;
        boolean flag = true;

        checkDriveNumberInput(used, total);
        compareUsedToTotalNumber(used, total);
        compareUsedToTotalSize(used, total);

        while (flag) {
            //greatestTotalSize = Collections.max(total);
            greatestTotalSize = getGreatestTotal(greatestTotalSize);
            positionOfHighestUsedSize = total.indexOf(greatestTotalSize);
            listOfGreatestTotals.add(greatestTotalSize);
            availableDiskSpace = getAvailableDiskSpace(positionOfHighestUsedSize);

            while (flag) {
                smallestUsedSize = Collections.min(used);
                positionOfLowestUsedSize = used.indexOf(smallestUsedSize);
                flag = dataRedistribution(smallestUsedSize,availableDiskSpace, positionOfLowestUsedSize, positionOfHighestUsedSize,
                                   greatestTotalSize);
            }

            for (int i = 0; i < total.size(); i++) {
                if (this.used.get(i) > 0 && this.used.get(i) < smallestUsedSize)
                    positionOfLowestUsedSize = i;
            }
            for (int i = 0; i < total.size(); i++) {
                if (getAvailableDiskSpace(i) > NULL && getAvailableDiskSpace(i) < total.get(i))
                    flag = true;
                else
                    flag = false;
            }

        }
        disksUsed = calculateDisksUsed(disksUsed);
        System.out.println("There were " + disksUsed + " needed.");
        return disksUsed;
    }

    public int getGreatestTotal(int greatestTotalNumber) {
        int max = 0;
        int index;
        if (greatestTotalNumber == 0)
            max = Collections.max(total);
        else {
            index = total.indexOf(greatestTotalNumber);
            for (int i = 0; i < total.size(); i++) {
                if (index != i && total.get(i) > max)
                    max = total.get(i);
            }
        }
        return max;
    }

    public int getAvailableDiskSpace(int position) {
        return total.get(position) - used.get(position);
    }

    public int calculateDisksUsed(int disksUsed) {
        for (int i = 0; i < used.size(); i++) {
            if (used.get(i) > 0)
                disksUsed++;
        }
        return disksUsed;
    }

    public boolean dataRedistribution(int smallestUsedSize, int availableDiskSpace, int positionOfLowestUsedSize,
                                   int positionOfGreatestTotalSize, int greatestTotalSize) {
        boolean key = true;
        if (smallestUsedSize <= availableDiskSpace) {
            //used.set(positionOfLowestUsedSize, NULL);
            //used.set(positionOfGreatestTotalSize, smallestUsedSize);
            setUsed(positionOfLowestUsedSize, NULL);
            setUsed(positionOfGreatestTotalSize, smallestUsedSize);
        }
        else {
            //used.set(positionOfLowestUsedSize, smallestUsedSize-availableDiskSpace);
            //used.set(positionOfGreatestTotalSize, greatestTotalSize);
            setUsed(positionOfLowestUsedSize, smallestUsedSize-availableDiskSpace);
            setUsed(positionOfGreatestTotalSize, greatestTotalSize);
            key = false;
        }
        return key;
    }

    public void checkDriveNumberInput(ArrayList<Integer> used, ArrayList<Integer> total) {
        if ((used.size() > 50 || used.size() == NULL) || (total.size() > 50 || total.size() == NULL)) {
            System.out.println("The number of drives cannot be less that 0 or more than 50");
            exit(0);
        }
    }

    public void compareUsedToTotalNumber(ArrayList<Integer> used, ArrayList<Integer> total) {
        if (used.size() != total.size()) {
            System.out.println("The two arrays must contain the same number of elements.");
            exit(0);
        }
    }

    public void compareUsedToTotalSize(ArrayList<Integer> used, ArrayList<Integer> total) {
        for (int i = 0; i < used.size(); i++) {
            if (used.get(i) > total.get(i)) {
                System.out.println("Used drive space can not be greater than total drive space");
                exit(0);
            }
            checkDriveSizeInput(used, total, i);
        }
    }

    public void checkDriveSizeInput(ArrayList<Integer> used, ArrayList<Integer> total, int size) {
        if ((1 > used.get(size) || used.get(size) > 1000) || (1 > total.get(size) || total.get(size) >1000)) {
            System.out.println("The size of each drive can not be less than 1 or more than 1000");
            exit(0);
        }
    }
}
