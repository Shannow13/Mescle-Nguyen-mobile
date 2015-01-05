package esiea.mescle_nguyen_mobile;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView textResult;
    ProgressBar progressBar;

    private MyBroadcastReceiver myBroadcastReceiver;
    private MyBroadcastReceiver_Update myBroadcastReceiver_Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView)findViewById(R.id.result);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        // Prepare MyParcelable passing to intentMyIntentService
        String msgToIntentService = "Pierre-Jean MESCLE et Hai NGUYEN";

        // Start MyIntentService
        Intent intentMyIntentService = new Intent(this, MyIntentService.class);
        intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, msgToIntentService);
        startService(intentMyIntentService);

        myBroadcastReceiver = new MyBroadcastReceiver();
        myBroadcastReceiver_Update = new MyBroadcastReceiver_Update();

        // Register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(MyIntentService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver, intentFilter);

        IntentFilter intentFilter_update = new IntentFilter(MyIntentService.ACTION_MyUpdate);
        intentFilter_update.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver_Update, intentFilter_update);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Un-register BroadcastReceiver
        unregisterReceiver(myBroadcastReceiver);
        unregisterReceiver(myBroadcastReceiver_Update);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(MyIntentService.EXTRA_KEY_OUT);
            textResult.setText(result);
        }
    }

    public class MyBroadcastReceiver_Update extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int update = intent.getIntExtra(MyIntentService.EXTRA_KEY_UPDATE, 0);
            progressBar.setProgress(update);
        }
    }

    // Push button -> Go to MainActivity 2
    public void BeerList(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Button settings (Action Bar)
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Et non, c'est un faux bouton désolé ^.^", Toast.LENGTH_SHORT).show();
        }

        // Button contacts (Action Bar)
        if (id == R.id.action_contacts) {
            Toast.makeText(getApplicationContext(), "Et non, c'est un faux bouton désolé ^.^", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
