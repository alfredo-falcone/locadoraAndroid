package br.com.falcone.locadora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Intent i = new Intent(getApplicationContext(), ListarBensActivity.class);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }

        });

        Button btAlugar = (Button) findViewById(R.id.btAlugarBem);
        btAlugar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //AlertDialog pergunta = new AlertDialog.Builder()
                Intent i = new Intent(getApplicationContext(), AlugarBemActivity.class);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }

        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Bem> InicializarBens()
    {
        List<Bem> bens = new ArrayList<>();
        bens = new ArrayList<>();
        bens.add(new Bem("Nome1", "genero1", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome2", "genero2", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome3", "genero3", "http://i.imgur.com/DvpvklR.png"));
        bens.add(new Bem("Nome4", "genero4", "http://i.imgur.com/DvpvklR.png"));
        return bens;
    }
}
