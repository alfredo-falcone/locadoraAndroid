package br.com.falcone.locadora;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import br.com.falcone.locadora.model.Bem;

/**
 * Created by Alfredo on 07/08/2017.
 */

public class AlarmeDevolucao extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getExtras().getLong(AlugarBemFragment.BEM_ID);

        Bem bem = Global.getInstance(context).getBemPorId(id);
        if(bem != null && Global.getInstance(context).IsBemAlugado(bem)) {
            String nome = bem.getNome();
            Toast.makeText(context, String.format(context.getString(R.string.texto_notificacao_devolucao), nome), Toast.LENGTH_SHORT).show();
            Notificar(context, bem);
        }

    }

    private void Notificar(Context context, Bem bem) {
        /*PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ListarBensActivity.class), 0);*/
        Intent resultIntent = new Intent("br.com.falcone.locadora.android.ACAO_CUSTOMIZADA",
                null, context, ListarBensActivity.class);
        resultIntent.putExtra(AlugarBemFragment.BEM_ID, bem.getId());


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(PrincipalActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getString(R.string.texto_titulo_notificacao_devolucao))
                        .setContentText(String.format(context.getString(R.string.texto_notificacao_devolucao), bem.getNome()));
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
