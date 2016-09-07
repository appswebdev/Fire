package college.minhal.fire.models;

/**
 * Created by ANDROID on 07/09/2016.
 */
public class ShoppingListItem {
    private String name;
    private String owner;
    private boolean purchased;
    private long purchasedAt;
    private String purchasedBy;

    //POJO:
    public ShoppingListItem() {
    }

    public ShoppingListItem(String name, String owner, boolean purchased, long purchasedAt, String purchasedBy) {
        this.name = name;
        this.owner = owner;
        this.purchased = purchased;
        this.purchasedAt = purchasedAt;
        this.purchasedBy = purchasedBy;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public long getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(long purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public String getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", purchased=" + purchased +
                ", purchasedAt=" + purchasedAt +
                ", purchasedBy='" + purchasedBy + '\'' +
                '}';
    }
}
