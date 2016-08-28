package college.minhal.fire;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by master on 28/08/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;

    //showProgress()
    protected void showProgress(String title, String message) {
        if (progressDialog == null)
            progressDialog =
                    new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();

    }

    protected void showProgress() {
        showProgress("Connecting to server", "Please wait");
    }

    protected void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void snackError(View v, Exception e) {
        Snackbar.make(v, e.getLocalizedMessage(),
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected String getUserID() {
        return getCurrentUser().getUid();
    }
}
