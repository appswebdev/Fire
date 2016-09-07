package college.minhal.fire;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import college.minhal.fire.models.ShoppingList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {
    private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<DataSnapshot> shoppingSnapshots = new ArrayList<>();
    private ShoppingListsAdapter adapter;


    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        testLogin();

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference().child("ShoppingLists").child(getUserID());

        shoppingLists = new ArrayList<>();
        initRecycler(v);
        updateData(ref);

        return v;
    }


    private void initRecycler(View v) {
        RecyclerView rvShoppingLists = (RecyclerView) v.findViewById(R.id.rvShoppingList);
        assert rvShoppingLists != null;
        rvShoppingLists.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ShoppingListsAdapter(shoppingSnapshots, getContext());
        rvShoppingLists.setAdapter(adapter);
    }


    private int getPositionForKey(String key) {
        for (int i = 0; i < shoppingSnapshots.size(); i++) {
            DataSnapshot s = shoppingSnapshots.get(i);
            if (s.getKey().equals(key)) {
                return i;
            }
        }
        throw new IllegalArgumentException("No Key found in the list");
    }

    private void updateData(DatabaseReference ref) {
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

    private void testLogin() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Toast.makeText(getContext(), "Hello, "
                                    + currentUser.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(),
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

    public void showAddList(View view) {
        AddNewListFragment dialog = new AddNewListFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    public String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
