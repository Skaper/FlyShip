package engine;

public class Time {
    private static final long DIVIDER_MICRO = 1_000;
    private static final long DIVIDER_MILLIS = 1_000_000;
    private static final long DIVIDER_SECONDS = 1_000_000_000;

    public static long getMillis(){
        return System.nanoTime() / DIVIDER_MILLIS;
    }
    public static long getMicrosecond(){
        return System.nanoTime() / DIVIDER_MICRO;
    }
    public static long getSeconds(){
        return System.nanoTime() / DIVIDER_SECONDS;
    }
}
