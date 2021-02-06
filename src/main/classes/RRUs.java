package main.classes;

public class RRUs extends BTS{

    private final Integer chain;

    public RRUs(String btsName, Integer subRack, Integer slot, Integer chain) {
        super(btsName, subRack, slot);
        this.chain = chain;
    }

    @Override
    public String getBtsName() {
        return btsName;
    }

    @Override
    public Integer getSubRack() {
        return subRack;
    }

    @Override
    public Integer getSlot() {
        return slot;
    }

    public Integer getChain() {
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
