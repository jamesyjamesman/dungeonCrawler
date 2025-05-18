package main;

//this is something I probably want to use a library for and/or multithreading
public class Parry {
    public static boolean parry() {
        StringBuilder preLine;
        StringBuilder postLine = null;
        System.out.print("|--o------------|\r");
        wait(500);
        for (int i = 0; i <= 10; i++) {
            preLine = new StringBuilder();
            postLine = new StringBuilder();
            String lineSegment = "-";
            preLine.append(lineSegment.repeat(10-i));
            postLine.append(lineSegment.repeat(i));
            System.out.print("|--o" + preLine + "*" + postLine + "-|\r");
            wait(100);
        }
        System.out.println("|--*" + postLine + "--|");
        return true;
    }

    public static void wait(int timeWaited) {
        try {
            Thread.sleep(timeWaited);
        } catch(InterruptedException e) {
            System.out.println("Stop that!");
        }
    }
}
