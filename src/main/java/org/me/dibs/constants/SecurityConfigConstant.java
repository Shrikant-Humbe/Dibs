package org.me.dibs.constants;

public enum SecurityConfigConstant {
    DEFAULT_FRONTEND_URL("http://localhost:5173/"),
    DEFAULT_FRONTEND_ORIGIN("http://localhost:5173"),
    BCRYPT_STRENGTH(10);

    private final Object value;

    SecurityConfigConstant(Object value) {
        this.value = value;
    }

    public String getValue() {
        return value.toString();
    }

    public int getIntValue() {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }
}
