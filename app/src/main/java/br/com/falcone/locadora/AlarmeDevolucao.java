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

/**
 * Created by Alfredo on 07/08/2017.
 */

public class AlarmeDevolucao extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nome = intent.getExtras().getString(AlugarBemFragment.BEM_NOME);
        Toast.makeText(context, String.format(context.getString(R.string.texto_notificacao_devolucao), nome), Toast.LENGTH_SHORT).show();
        Notificar(context, nome);

    }

    private void Notificar(Context context, String nomeBem) {
        /*PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ListarBensActivity.class), 0);*/
        Intent resultIntent = new Intent("br.com.falcone.locadora.android.ACAO_CUSTOMIZADA",
                null, context, ListarBensActivity.class);
        resultIntent.putExtra(AlugarBemFragment.BEM_NOME, nomeBem );


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
                        .setContentText(String.format(context.getString(R.string.texto_notificacao_devolucao), nomeBem));
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
