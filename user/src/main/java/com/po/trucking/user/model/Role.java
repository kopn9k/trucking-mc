package com.po.trucking.user.model;

public enum Role {
    SYS_ADMIN("SYS_ADMIN"),
    ADMIN("ADMIN"),
    DISPATCHER("DISPATCHER"),
    MANAGER("MANAGER"),
    DRIVER("DRIVER"),
    OWNER("OWNER");

    private String value;

    public String getValue() {
        return value;
    }

    Role(String value) {
        this.value = value;
    }

}
