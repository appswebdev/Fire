package college.minhal.fire.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by master on 24/08/16.
 */

//Empty Constructor & public Getters And Setters
public class ShoppingList implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.listName);
        dest.writeString(this.owner);
    }

    protected ShoppingList(Parcel in) {
        this.listName = in.readString();
        this.owner = in.readString();
    }

    public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel source) {
            return new ShoppingList(source);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[size];
        }
    };
}
