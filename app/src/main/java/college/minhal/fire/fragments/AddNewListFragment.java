package college.minhal.fire.fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import college.minhal.fire.R;
import college.minhal.fire.models.ShoppingList;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewListFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_new_list, container, false);
        FancyButton btnSave = (FancyButton) v.findViewById(R.id.btnSave);
        final EditText etListName = (EditText) v.findViewById(R.id.etListName);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Save to firebase
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ShoppingList l = new ShoppingList(etListName.getText().toString(), uid);

                FirebaseDatabase.getInstance().getReference().child("ShoppingLists").child(uid).
                        push().setValue(l).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                            }
                        }).
                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                dismiss();
            }
        });


        return v;
    }

}
