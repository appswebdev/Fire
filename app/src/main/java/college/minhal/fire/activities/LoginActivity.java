package college.minhal.fire.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import college.minhal.fire.R;
import college.minhal.fire.models.User;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GOOGLE = 12;
    EditText etEmail;
    EditText etPassword;
    private GoogleApiClient mGoogleApiClient;
    private View btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnGoogle = findViewById(R.id.btnGoogleLogin);
        assert btnGoogle != null;

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .enableAutoManage(this, null)
                .build();
    }

    private void signInWithGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, REQUEST_CODE_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOOGLE && resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = result.getSignInAccount();

            //
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            FirebaseAuth.getInstance().signInWithCredential(credential).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = authResult.getUser();
                    User u = new User(user.getDisplayName(), user.getEmail());

                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(user.getUid()).setValue(u);
                    gotoMain();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showError(e, btnGoogle);
                }
            });


        }
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

        if (getPassword() == null || getPassword().length() < 6) {
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


    private void showError(Exception e, View view) {
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