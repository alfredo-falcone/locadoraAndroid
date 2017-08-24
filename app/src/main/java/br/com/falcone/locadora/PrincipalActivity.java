package br.com.falcone.locadora;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private FusedLocationProviderClient mFusedLocationClient;

    public static final String IS_BIG_NOTIFICATION = "isBigNotification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Button btBens = (Button) findViewById(R.id.btBens);
        btBens.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //AlertDialog pergunta = new AlertDialog.Builder()
                ListarBens();
            }

        });

        Button btAlugar = (Button) findViewById(R.id.btAlugarBem);
        btAlugar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //createSimpleNotification();
                //AlertDialog pergunta = new AlertDialog.Builder()
                Alugar();
            }

        });

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Global.getInstance(PrincipalActivity.this).setLocation(location);
                            }
                        }
                    });

        }

    }

    private void ListarBens() {
        Intent i = new Intent(getApplicationContext(), ListarBensActivity.class);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void Alugar() {
        Intent i = new Intent(getApplicationContext(), AlugarBemActivity.class);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    private void init(){
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alugar_bem) {
            Alugar();
            return true;
        }
        else if (id == R.id.action_listar_bens) {
            ListarBens();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void createSimpleNotification() {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificação simples")
                        .setContentText("Clique para saber mais...");

        Intent resultIntent = new Intent(this, ListarBensActivity.class);
        resultIntent.putExtra(IS_BIG_NOTIFICATION,  false);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(PrincipalActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager.notify(10000, mBuilder.build());

    }*/
}
