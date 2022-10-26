import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> used = new ArrayList<>();
        ArrayList<Integer> total = new ArrayList<>();

        used.add(300);
        used.add(525);
        used.add(110);

        total.add(350);
        total.add(600);
        total.add(115);

        DiskSpace diskSpace = new DiskSpace(used, total);
        diskSpace.minDrives(used, total);
    }
}
