package com.beta.limited.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoleEnum {
    ADMIN(1, "ROLE_ADMIN"),
    MANAGER(2,"ROLE_MANAGER"),
    RUNNER(2,"ROLE_RUNNER")
    ;

    private int code;
    private String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    RoleEnum() {
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    public static Optional<RoleEnum> valueOf(int code) {
        return Arrays.stream(RoleEnum.values()).filter(authority -> authority.code == code).findFirst();
    }
}
