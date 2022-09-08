/*
 * Author: Pawel Piesniewski
 * ID: A049473
 */
package com.volvo.project.enums;

public enum HostName {

    GimliA1("gimli-a1"),
    EowynB3("eowyn-b3");

    private final String value;

    private HostName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}