package college.minhal.fire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import college.minhal.fire.R;
import college.minhal.fire.fragments.ShoppingListFragment;

public class ShoppingListsActivity extends BaseActivity {


    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        testLogin();

    }


    @Override
    protected void onStart() {
        super.onStart();
        testLogin();

    }


    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

    private void testLogin() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {

                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container, new ShoppingListFragment()).
                            commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                    );
                    startActivity(intent);
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

}
