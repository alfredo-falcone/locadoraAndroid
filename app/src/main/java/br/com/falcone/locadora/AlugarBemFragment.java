package br.com.falcone.locadora;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlugarBemFragment extends Fragment {

    public static final String BEM_NOME = "bem.nome";
    public AlugarBemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alugar_bem, container, false);
        ImageButton imgBtHoraDevolucao = view.findViewById(R.id.imgBtHoraDevolucao);
        AppCompatSpinner spinBens = view.findViewById(R.id.spinBens);

        // Create an ArrayAdapter using the string array and a default spinner layout

        BemAdapter adapter = new BemAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, Global.getInstance().getBens());

        //android.R.layout.simple_spinner_dropdown_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinBens.setAdapter(adapter);


        imgBtHoraDevolucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendarAlarme();
            }
        });


        Button btGravarAluguel = view.findViewById(R.id.btGravarAluguel);
        btGravarAluguel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                EditText tbHoraDevolucao = AlugarBemFragment.this.getActivity().findViewById(R.id.tbHoraDevolucao);
                if(TextUtils.isEmpty(tbHoraDevolucao.getText())){
                    AlertDialog validacao = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.texto_preencher_hora_devolucao)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .create();

                    validacao.show();
                }
                else{

                AlertDialog pergunta = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.texto_confirma_gravacao)
                        .setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alugar();
                            }
                        }).create();

                    pergunta.show();
                }

            }

        });
        //imgBtHoraDevolucao
        return view;
    }

    private void alugar() {
        EditText tbHoraDevolucao = AlugarBemFragment.this.getActivity().findViewById(R.id.tbHoraDevolucao);
        AppCompatSpinner spinBens = AlugarBemFragment.this.getActivity().findViewById(R.id.spinBens);
        int hourOfDay = Integer.parseInt(tbHoraDevolucao.getText().subSequence(0, 2).toString());
        int minute = Integer.parseInt(tbHoraDevolucao.getText().subSequence(3, 5).toString());

        Intent it = new Intent(AlugarBemFragment.this.getActivity(), AlarmeDevolucao.class);
        it.putExtra(BEM_NOME, ((Bem)spinBens.getSelectedItem()).getNome());
        PendingIntent pit = PendingIntent.getBroadcast(
                AlugarBemFragment.this.getActivity(), 0, it, 0);

        AlarmManager alarmManager = (AlarmManager)
                getActivity().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pit);


        Toast.makeText(getContext(), R.string.toast_alarme_agendado, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getActivity().getApplicationContext(), PrincipalActivity.class);
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(i);
        }
    }
    private void agendarAlarme(){
        TimePickerDialog.OnTimeSetListener tratador =
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        EditText tbHoraDevolucao = AlugarBemFragment.this.getActivity().findViewById(R.id.tbHoraDevolucao);
                        tbHoraDevolucao.setText(String.format(Locale.getDefault(), "%1$02d:%2$02d",  hourOfDay, minute));
                    }
                };
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                tratador,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

}
