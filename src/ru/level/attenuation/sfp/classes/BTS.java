package ru.level.attenuation.sfp.classes;

public abstract class BTS {

    private final String BTS_name;

    public BTS(String BTS_name) {
        this.BTS_name = BTS_name;
    }

    public String getBTS_name() {
        return BTS_name;
    }

    public abstract String display();
}
