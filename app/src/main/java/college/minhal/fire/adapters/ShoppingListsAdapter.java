package college.minhal.fire.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import college.minhal.fire.R;
import college.minhal.fire.fragments.ListItemsFragment;
import college.minhal.fire.models.ShoppingList;

/**
 * Created by master on 28/08/16.
 */
public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<DataSnapshot> shoppingSnapshots;
    // private ArrayList<ShoppingList> shoppingLists;
    private FragmentActivity activity;

    //Constructor:
/*
    public ShoppingListsAdapter(ArrayList<ShoppingList> shoppingLists, Context activity) {
        //this.shoppingLists = shoppingLists;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }
*/

    public ShoppingListsAdapter(ArrayList<DataSnapshot> shoppingSnapshots, FragmentActivity activity) {
        this.activity = activity;
        this.shoppingSnapshots = shoppingSnapshots;
        this.inflater = LayoutInflater.from(activity);
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
        final DataSnapshot snapshot = shoppingSnapshots.get(position);
        final ShoppingList list = snapshot.getValue(ShoppingList.class);
        //bind the data to the Views in the viewHolder
        holder.tvListName.setText(list.getListName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, ListItemsFragment.newInstance(snapshot.getKey(), list)).
                        addToBackStack("List").
                        commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingSnapshots.size();
    }

    //an inner class that extends ViewHolder
    //and does findViewByID:

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView tvListName;
        RelativeLayout layout;

        public ShoppingListViewHolder(View v) {
            super(v);
            tvListName = (TextView) v.findViewById(R.id.tvListName);
            layout = (RelativeLayout) v.findViewById(R.id.layout);
        }
    }
}
