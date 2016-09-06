package college.minhal.fire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import college.minhal.fire.models.ShoppingList;

/**
 * Created by master on 28/08/16.
 */
public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<DataSnapshot> shoppingSnapshots;
    // private ArrayList<ShoppingList> shoppingLists;
    private Context context;

    //Constructor:
/*
    public ShoppingListsAdapter(ArrayList<ShoppingList> shoppingLists, Context context) {
        //this.shoppingLists = shoppingLists;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
*/

    public ShoppingListsAdapter(ArrayList<DataSnapshot> shoppingSnapshots, ShoppingListsActivity context) {
        this.context = context;
        this.shoppingSnapshots = shoppingSnapshots;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ShoppingListViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.
                inflate(R.layout.shipping_lists_item,
                        parent,
                        false);

        return new ShoppingListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            ShoppingListViewHolder holder,
            int position) {
        //Get the data at the position:
        ShoppingList t = shoppingSnapshots.get(position).getValue(ShoppingList.class);
        //bind the data to the Views in the viewHolder
        holder.tvListName.setText(t.getListName());
    }

    @Override
    public int getItemCount() {
        return shoppingSnapshots.size();
    }

    //an inner class that extends ViewHolder
    //and does findViewByID:

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView tvListName;

        public ShoppingListViewHolder(View v) {
            super(v);
            tvListName = (TextView) v.findViewById(R.id.tvListName);
        }
    }
}
