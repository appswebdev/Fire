package college.minhal.fire;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewListFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_new_list, container, false);
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        final EditText etListName = (EditText) v.findViewById(R.id.etListName);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save to firebase


                String owner = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                ShoppingList shoppingList = new ShoppingList(
                        etListName.
                        getText().toString(),owner);

                FirebaseDatabase.getInstance().getReference()
                        .child("ShoppingLists").push()
                        .setValue(shoppingList);

                dismiss();
            }
        });

        return v;
    }

}
