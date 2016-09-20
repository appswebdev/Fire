package college.minhal.fire.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import college.minhal.fire.R;
import college.minhal.fire.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void login(final View view) {
        if (!validate()) {
            return;
        }

        showProgress();
        FirebaseAuth.getInstance().
                signInWithEmailAndPassword(getEmail(), getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        gotoMain();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e, view);
                    }
                });
    }

    private boolean validate() {

        boolean isValid = true;
        if (getEmail() == null || getEmail().isEmpty()) {
            etEmail.setError("Email Must not be empty");
            isValid = false;
        } else if (getEmail() != null && !getEmail().contains("@")) {
            etEmail.setError("Email Must contain @");
            isValid = false;
        }

        if (getPassword() == null ||getPassword().length() < 6) {
            etPassword.setError("Password must contain at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    public void register(final View view) {
        if (!validate())
            return;
        showProgress();
        FirebaseAuth.getInstance().
                createUserWithEmailAndPassword(getEmail(), getPassword()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Save the user:
                        String uid = authResult.getUser().getUid();
                        User u = new User(getEmail().split("@")[0], getEmail());
                        FirebaseDatabase.getInstance().getReference().
                                child("Users").child(uid).setValue(u);
                        gotoMain();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(e, view);
                    }
                });
    }

    ProgressDialog dialog;

    private void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Logging you in");
            dialog.setMessage("Please wait");
        }
        dialog.show();
    }

    private void hideProgress() {
        if (dialog != null)
            dialog.dismiss();
    }


    private void showError(Exception e, View view ) {
        hideProgress();
        Snackbar.make(view,
                e.getLocalizedMessage(),
                Snackbar.LENGTH_INDEFINITE
        ).setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    private void gotoMain() {
        hideProgress();
        Intent intent = new Intent(this, ShoppingListsActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        startActivity(intent);
    }

    public String getEmail() {
        return etEmail.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }
}