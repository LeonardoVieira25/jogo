package util;

public class Time {
    private static double startTime = System.nanoTime();
    public static double getTime() {
        return (System.nanoTime() - startTime) / 1e9;
    }
}
