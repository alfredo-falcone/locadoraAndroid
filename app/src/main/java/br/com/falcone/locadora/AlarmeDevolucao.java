package br.com.falcone.locadora;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Alfredo on 07/08/2017.
 */

public class AlarmeDevolucao extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nome = intent.getExtras().getString(AlugarBemFragment.BEM_NOME);
        Toast.makeText(context, "Hora da devolução do bem " + nome, Toast.LENGTH_SHORT).show();
    }
}
