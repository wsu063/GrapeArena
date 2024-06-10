package com.podoarena.constant;

public enum SeatStatus {
    SELECTED, AVAILABLE, NONE;

    public static SeatStatus fromInt(int value) {
        switch (value) {
            case 0: return NONE;
            case 1: return AVAILABLE;
            case 2: return SELECTED;
            default: throw new IllegalArgumentException("잘못된 값입니다.");
        }
    }
}
