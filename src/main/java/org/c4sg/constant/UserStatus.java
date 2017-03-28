package org.c4sg.constant;

public enum UserStatus {

    ACTIVE("A"), PENDING("P"), DELETED("D");

    private String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getStatus(String status) {
        for (UserStatus e : UserStatus.values()) {
            if (e.getValue().equalsIgnoreCase(status)) {
                return e.name();
            }
        }
        return PENDING.name();
    }
}
