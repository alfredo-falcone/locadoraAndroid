package br.com.falcone.locadora;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.falcone.locadora.Util.OkHttpUtil;
import br.com.falcone.locadora.model.Aluguel;
import br.com.falcone.locadora.model.Bem;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlugarBemFragment extends Fragment {

    public static final String BEM_ID = "bem.id";
    public static final String ID_ALARME = "alarme.id";
    public AlugarBemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alugar_bem, container, false);
        ImageButton imgBtHoraDevolucao = view.findViewById(R.id.imgBtHoraDevolucao);
        AppCompatSpinner spinBens = view.findViewById(R.id.spinBens);
        EditText tbHoraDevolucao = view.findViewById(R.id.tbHoraDevolucao);

        List<Bem> bensFiltrados = new ArrayList<>();
        for (Bem bemAtual: Global.getInstance(getContext()).getBens()) {
            if(!Global.getInstance(getContext()).IsBemAlugado(bemAtual))
                bensFiltrados.add(bemAtual);
        }
        BemAdapter adapter = new BemAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, bensFiltrados);

        //android.R.layout.simple_spinner_dropdown_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinBens.setAdapter(adapter);


        imgBtHoraDevolucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendarAlarme();
            }
        });

        tbHoraDevolucao.setOnClickListener(new View.OnClickListener() {
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
                else {

                    AlertDialog pergunta = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.texto_confirma_gravacao)
                            .setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if(Global.getInstance(getContext()).IsConnected())
                                        new AssyncLoad().execute(0);

                                }
                            }).create();

                    pergunta.show();
                }

            }

        });
        //imgBtHoraDevolucao
        return view;
    }

    private void alugar(Double percentualAjustePreco) {
        EditText tbHoraDevolucao = AlugarBemFragment.this.getActivity().findViewById(R.id.tbHoraDevolucao);
        AppCompatSpinner spinBens = AlugarBemFragment.this.getActivity().findViewById(R.id.spinBens);
        int hourOfDay = Integer.parseInt(tbHoraDevolucao.getText().subSequence(0, 2).toString());
        int minute = Integer.parseInt(tbHoraDevolucao.getText().subSequence(3, 5).toString());
        Bem bemSelecionado = (Bem)spinBens.getSelectedItem();

        Integer idAlarme = null;
        int flag = 0;
        if(Global.getInstance(getContext()).IsBemAlugado(bemSelecionado)) {
            flag = PendingIntent.FLAG_UPDATE_CURRENT;
            idAlarme = Global.getInstance(getContext()).GetIdAlarme(bemSelecionado);
        }
        else {
            idAlarme = Global.getInstance(getContext()).getProximaChave();
        }

        Intent it = new Intent(AlugarBemFragment.this.getActivity(), AlarmeDevolucao.class);
        it.putExtra(BEM_ID, bemSelecionado.getId());
        PendingIntent pit = PendingIntent.getBroadcast(
                AlugarBemFragment.this.getActivity(), idAlarme, it, flag);

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



        Calendar dataAluguel = Calendar.getInstance();
        long miliSegundos = calendar.getTimeInMillis() - dataAluguel.getTimeInMillis();
        long minutos = miliSegundos / (1000 * 60);
        long horas  = minutos / 60;
        minutos = minutos - (horas * 60);
        if(minutos > 30)
            horas++;

        //double percentualAjustePreco = OkHttpUtil.CalcularPercentualAjustePreco(Global.getInstance(getContext()).getLocation());
        double valorTotal = bemSelecionado.getPrecoHora() * percentualAjustePreco * horas;
        Aluguel aluguel = new Aluguel(bemSelecionado, valorTotal);

        Global.getInstance(getContext()).getAlugueis().put(idAlarme, aluguel);

        Toast.makeText(getContext(), String.format(getString( R.string.toast_alarme_agendado), aluguel.getValorTotal()), Toast.LENGTH_LONG).show();

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


    public class AssyncLoad extends AsyncTask<Integer, Void, Double> {

        @Override
        protected Double doInBackground(Integer ... inteiros) {
            /*switch(inteiros[0]) {
                case TIPO_COMPONENTE_OKHTTP:
                    return Util.CarregarArtigosOkHTTP();
                case TIPO_COMPONENTE_RETROFIT:
                    return Util.CarregarArtigosRetrofit();
                default:
                    return Util.CarregarArtigos();

            }*/
            return Global.getInstance(getContext()).CalcularPercentualAjustePreco();

        }

        @Override
        protected void onPreExecute() {
            ProgressBar barraProgresso = getActivity().findViewById(R.id.barraProgresso);

            barraProgresso.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Double percentual) {
            super.onPostExecute(percentual);
            ProgressBar barraProgresso = getActivity().findViewById(R.id.barraProgresso);
            barraProgresso.setVisibility(View.GONE);

            alugar(percentual);
        }
    }

}
