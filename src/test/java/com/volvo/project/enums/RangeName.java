package com.volvo.project.enums;

/**
 * This class contains dictionary data representing Ranges
 * @author a049473
 *
 */

public enum RangeName {

    CONVENTIONAL("Conventional Vehicle"),
    HD_VEHICLE("HD Vehicle");

    private final String value;

    private RangeName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
