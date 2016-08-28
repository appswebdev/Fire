package college.minhal.fire;

/**
 * Created by master on 24/08/16.
 */
public class ShoppingList {
    private String listName;
    private String owner;

    public ShoppingList(String listName, String owner) {
        this.listName = listName;
        this.owner = owner;
    }

    //POJO:
    public ShoppingList() {
    }

    //POJO:
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "listName='" + listName + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
