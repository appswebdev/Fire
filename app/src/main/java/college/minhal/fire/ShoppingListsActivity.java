package college.minhal.fire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference().child("ShoppingLists");
        final ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ShoppingList shoppingList = child.getValue(ShoppingList.class);
                    shoppingLists.add(shoppingList);
                }
                initRecycler(shoppingLists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initRecycler(ArrayList<ShoppingList> shoppingLists) {
        RecyclerView rvShoppingList = (RecyclerView) findViewById(R.id.rvShoppingList);
        ShoppingListsAdapter adapter = new ShoppingListsAdapter(shoppingLists, this);
        rvShoppingList.setLayoutManager(new LinearLayoutManager(this));
        rvShoppingList.setAdapter(adapter);
    }
}
