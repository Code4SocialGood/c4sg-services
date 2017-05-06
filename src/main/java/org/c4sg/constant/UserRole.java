package org.c4sg.constant;

public enum UserRole {

    VOLUNTEER("V"), ORGANIZATION("O"), ADMIN("A");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getUserRole(String role) {
        for (UserRole e : UserRole.values()) {
            if (e.getValue().equalsIgnoreCase(role)) {
                return e.name();
            }
        }
        return VOLUNTEER.name();
    }
}
