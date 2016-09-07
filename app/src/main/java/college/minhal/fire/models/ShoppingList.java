package college.minhal.fire.models;

/**
 * Created by master on 24/08/16.
 */

//Empty Constructor & public Getters And Setters
public class ShoppingList {
    private String listName;
    private String owner;

    //POJO:
    public ShoppingList() {
    }

    public ShoppingList(String listName, String owner) {

        this.listName = listName;
        this.owner = owner;
    }

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
        return   listName ;
    }
}
