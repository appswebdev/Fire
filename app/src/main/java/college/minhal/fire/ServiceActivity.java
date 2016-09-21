package college.minhal.fire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    int i = 0;
    public void startTheService(View view) {

        Intent intent = new Intent(this, MyStartedService.class);
        intent.putExtra("Message", "Hello " + i);
        i++;
        startService(intent);
    }

    public void stopTheService(View view) {
        Intent intent = new Intent(this, MyStartedService.class);
        stopService(intent);
    }
}
