package college.minhal.fire.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import college.minhal.fire.R;
import college.minhal.fire.models.ShoppingListItem;

/**
 * Created by master on 28/08/16.
 */
public class ShoppingListsItemsAdapter extends
        RecyclerView.Adapter<ShoppingListsItemsAdapter.ShoppingListItemsViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<DataSnapshot> shoppingSnapshots;
    // private ArrayList<ShoppingList> shoppingLists;
    private FragmentActivity activity;

    //Constructor:
    public ShoppingListsItemsAdapter(FragmentActivity activity, String listKey) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        shoppingSnapshots = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().
                child("ShoppingListItems").
                child(listKey).
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        shoppingSnapshots.add(dataSnapshot);
                        notifyItemInserted(shoppingSnapshots.size() - 1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        int position = positionForKey(dataSnapshot.getKey());
                        shoppingSnapshots.set(position, dataSnapshot);
                        notifyItemChanged(position);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        int position = positionForKey(dataSnapshot.getKey());
                        shoppingSnapshots.remove(position);
                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private int positionForKey(String key) {
        for (int i = 0; i < shoppingSnapshots.size(); i++) {
            DataSnapshot snapshot = shoppingSnapshots.get(i);
            if (snapshot.getKey().equals(key))
                return i;
        }
        throw new IllegalArgumentException("No Such Key");
    }

    @Override
    public ShoppingListItemsViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.
                inflate(R.layout.shopping_list_item,
                        parent,
                        false);

        return new ShoppingListItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            ShoppingListItemsViewHolder holder,
            int position) {
        //Get the data at the position:
        final DataSnapshot snapshot = shoppingSnapshots.get(position);
        final ShoppingListItem listItem = snapshot.getValue(ShoppingListItem.class);
        //bind the data to the Views in the viewHolder
        holder.tvItemName.setText(listItem.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, listItem.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingSnapshots.size();
    }

    //an inner class that extends ViewHolder
    //and does findViewByID:

    class ShoppingListItemsViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        RelativeLayout layout;

        public ShoppingListItemsViewHolder(View v) {
            super(v);
            tvItemName = (TextView) v.findViewById(R.id.tvItemName);
            layout = (RelativeLayout) v.findViewById(R.id.layout);
        }
    }
}
