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
    public static int toInt(SeatStatus seatStatus) {
        switch (seatStatus) {
            case NONE: return 0;
            case AVAILABLE: return 1;
            case SELECTED: return 2;
            default: throw new IllegalArgumentException("잘못된 값입니다.");
        }
    }
}
