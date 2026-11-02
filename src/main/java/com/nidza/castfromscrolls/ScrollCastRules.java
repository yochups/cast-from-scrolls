package com.nidza.castfromscrolls;

public final class ScrollCastRules {
    private ScrollCastRules() {
    }

    public static boolean learningRequired(boolean scrollCast, boolean original) {
        return !scrollCast && original;
    }

    public static int recastCount(boolean scrollCast, int original) {
        return scrollCast ? 0 : original;
    }

    public static boolean retainScroll(boolean activeRecast) {
        return activeRecast;
    }
}
