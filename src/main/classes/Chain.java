package main.classes;

public class Chain extends BTS {
    int chainNo;
    Integer headPort;
    Integer tailSubRack;
    Integer tailSlot;
    Integer tailPort;

    public Chain(String btsName, int chainNo, Integer subRack, Integer slot, Integer headPort, Integer tailSubRack,
                 Integer tailSlot, Integer tailPort) {
        super(btsName, subRack, slot);
        this.chainNo = chainNo;
        this.headPort = headPort;
        this.tailSubRack = tailSubRack;
        this.tailSlot = tailSlot;
        this.tailPort = tailPort;
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

    public int getChainNo() {
        return chainNo;
    }

    public void setChainNo(Integer subRack) {
        this.chainNo = subRack;
    }

    public Integer getHeadPort() {
        return headPort;
    }

    public Integer getTailSubRack() {
        return tailSubRack;
    }

    public Integer getTailSlot() {
        return tailSlot;
    }

    public Integer getTailPort() {
        return tailPort;
    }

    @Override
    public String display() {
        return "Chain{" +
                "btsName='" + btsName + '\'' +
                ", chainNo=" + chainNo +
                ", subRack=" + subRack +
                ", slot=" + slot +
                ", headPort=" + headPort +
                ", tailSubRack=" + tailSubRack +
                ", tailSlot=" + tailSlot +
                ", tailPort=" + tailPort +
                '}';
    }
}
