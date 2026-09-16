package org.me.dibs.constants;

public enum SecuritySecret {
    JWT_SECRET("JWT_SECRET", "234324324234asdsadadwasdasdasdasdcasdawasdasdcweqdasdweawdsdadad");

    private final String envVar;
    private final String defaultValue;

    SecuritySecret(String envVar, String defaultValue) {
        this.envVar = envVar;
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        String envValue = System.getenv(envVar);
        return (envValue != null && !envValue.trim().isEmpty()) ? envValue : defaultValue;
    }
}
