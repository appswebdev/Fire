package college.minhal.fire;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import college.minhal.fire.models.ShoppingList;

public class ShoppingListsActivity extends BaseActivity {

    private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<DataSnapshot> shoppingSnapshots = new ArrayList<>();
    private ShoppingListsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        testLogin();

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference().child("ShoppingLists").child(getUserID());

        shoppingLists = new ArrayList<ShoppingList>();

        updateData(ref);

        //Fetch data ONCE, if changed? Fetch again.
        /*
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shoppingLists.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ShoppingList list = child.getValue(ShoppingList.class);
                    shoppingLists.add(list);
                }
                initRecycler();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


    }


    private int getPositionForKey(String key){
        for (int i = 0; i < shoppingSnapshots.size(); i++) {
            DataSnapshot s = shoppingSnapshots.get(i);
            if (s.getKey().equals(key)){
                return i;
            }
        }
       throw new IllegalArgumentException("No Key found in the list");
    }
    private void updateData(DatabaseReference ref) {

        RecyclerView rvShoppingLists = (RecyclerView) findViewById(R.id.rvShoppingList);
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingListsAdapter(shoppingSnapshots, this);
        rvShoppingLists.setAdapter(adapter);


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                shoppingSnapshots.add(dataSnapshot);
                adapter.notifyItemInserted(shoppingSnapshots.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Integer position = getPositionForKey(dataSnapshot.getKey());
                //replace in the list
                shoppingSnapshots.set(position, dataSnapshot);
                //notify the adapter
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int position = getPositionForKey(dataSnapshot.getKey());
                shoppingSnapshots.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

 /*   private void initRecycler() {
        RecyclerView rvShoppingList = (RecyclerView) findViewById(R.id.rvShoppingList);
        assert rvShoppingList != null;
        rvShoppingList.setAdapter(new ShoppingListsAdapter(shoppingLists, ShoppingListsActivity.this));
        rvShoppingList.setLayoutManager(new LinearLayoutManager(ShoppingListsActivity.this));

    }*/


    private void testLogin() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(ShoppingListsActivity.this, "Hello, "
                                    + currentUser.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ShoppingListsActivity.this,
                            LoginActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                    );
                    startActivity(intent);
                }
            }
        });
    }

/*

    private void initRecycler(ArrayList<ShoppingList> shoppingLists) {
        RecyclerView rvShoppingList = (RecyclerView) findViewById(R.id.rvShoppingList);
        ShoppingListsAdapter adapter = new ShoppingListsAdapter(shoppingLists, this);
        rvShoppingList.setLayoutManager(new LinearLayoutManager(this));
        rvShoppingList.setAdapter(adapter);
    }
*/

    public void showAddList(View view) {
        AddNewListFragment dialog = new AddNewListFragment();
        dialog.show(getFragmentManager(), "dialog");
    }
}
