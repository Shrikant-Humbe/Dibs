package org.me.dibs.constants;

public enum JwtConstant {
    EXPIRATION_TIME(1000L * 60 * 50),
    HEADER_NAME("Authorization"),
    TOKEN_PREFIX("Bearer ");

    private final Object value;

    JwtConstant(Object value) {
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
