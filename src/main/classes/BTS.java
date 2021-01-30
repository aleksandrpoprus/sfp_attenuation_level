package main.classes;

public abstract class BTS {

    final String btsName;
    int subRack;
    final int slot;

    public BTS(String btsName,
               int subRack,
               int slot) {
        this.btsName = btsName;
        this.subRack = subRack;
        this.slot = slot;
    }

    public abstract String getBtsName();

    public abstract int getSubRack();

    public abstract int getSlot();

    public abstract String display();

}
