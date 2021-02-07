package main.classes;

public abstract class BTS {

    String btsName;
    Integer subRack;
    Integer slot;

    public BTS(String btsName, int subRack, int slot) {
        this.btsName = btsName;
        this.subRack = subRack;
        this.slot = slot;
    }

    public abstract String getBtsName();

    public abstract Integer getSubRack();

    public abstract Integer getSlot();

    public abstract String display();

}
