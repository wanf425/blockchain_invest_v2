package com.wt.blockchainivest.domain.trasaction;

public class Constants {

    private int id;
    private String type;
    private String key;
    private String value;

    public Constants() {

    }

    public Constants(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
