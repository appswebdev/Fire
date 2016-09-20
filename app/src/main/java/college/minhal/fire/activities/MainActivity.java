package college.minhal.fire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import college.minhal.fire.R;
import college.minhal.fire.models.ShoppingList;

public class MainActivity extends BaseActivity {
    Button btnSave;
    EditText etTitle;
    EditText etDescription;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testLogin();
        findViews();

        //FirebaseDatabase.getInstance().getReference().onDisconnect().updateChildren(//)
    }

    private void findViews() {
        btnSave = (Button) findViewById(R.id.btnSave);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);

    }

    private void testLogin() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null && isFirst) {
                    Toast.makeText(MainActivity.this, "Hello, "
                                    + currentUser.getEmail(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTodo(View view) {
        showProgress();
        btnSave.setEnabled(false);
        //GET a reference to database
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference()
                        .child("ShoppingLists");

        //add a new row:
        //ref.push
        ShoppingList t = new ShoppingList(
                etTitle.getText().toString(),
                etDescription.getText().toString());
        //set Value (POJO)
        ref.push().setValue(t).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                hideProgress();
                btnSave.setEnabled(true);
                etTitle.setText("");
                etDescription.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                snackError(btnSave, e);
                btnSave.setEnabled(true);
            }
        });
    }
}
