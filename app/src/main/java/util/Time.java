package util;

public class Time {
    private static double startTime = System.nanoTime();
    public static float getTime() {
        return (float)((System.nanoTime() - startTime) / 1e9);
    }
}
