package main.classes;

public class Chains extends BTS{

    private final int chain;

    public Chains(String btsName, int subRack, int slot, int chain) {
        super(btsName, subRack, slot);
        this.chain = chain;
    }

    @Override
    public String getBtsName() {
        return btsName;
    }

    @Override
    public int getSubRack() {
        return subRack;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    public int getChain() {
        return chain;
    }

    @Override
    public String display() {
        return "Chains{" +
                "btsName='" + btsName + '\'' +
                ", subRack=" + subRack +
                ", slot=" + slot +
                ", chain=" + chain +
                '}';
    }
}
