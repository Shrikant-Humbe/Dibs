package org.me.dibs.constants;

public enum CookieConstant {
    COOKIE_NAME("jwtoken"),
    PATH("/"),
    SAME_SITE("None"),
    MAX_AGE(24L * 60 * 60);

    private final Object value;

    CookieConstant(Object value) {
        this.value = value;
    }

    public String getValue() {
        return value.toString();
    }

    public long getLongValue() {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return Long.parseLong(value.toString());
    }
}
