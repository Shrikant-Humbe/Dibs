package org.me.dibs.constants;

public enum UserRoleConstant {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String roleName;

    UserRoleConstant(String roleName) {
        this.roleName = roleName;
    }

    public String getValue() {
        return roleName;
    }
}
