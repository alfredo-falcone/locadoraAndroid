package br.com.falcone.locadora;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlugarBemFragment extends Fragment {

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
        //imgBtHoraDevolucao
        return view;
    }

    private void agendarAlarme(){
        TimePickerDialog.OnTimeSetListener tratador =
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        /*Intent it = new Intent(AlugarBemFragment.this.getActivity(), AlarmeDevolucao.class);

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
                                pit);*/
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
