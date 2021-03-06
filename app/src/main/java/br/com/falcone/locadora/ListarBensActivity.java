package br.com.falcone.locadora;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.falcone.locadora.model.Bem;

public class ListarBensActivity extends AppCompatActivity implements CadastroBemFragment.DialogCadastroBemListener {

    private BemAdapter mAdapter;
    private ListView mLstBens;
    private FragmentManager mFragmentManager;
    public static final String TAG_DLG_CADASTRO = "dlgCadastroBem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_bens);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bem bem = ListarBensActivity.this.mAdapter.getItem(posicao);
                CadastroBemFragment fragment = (CadastroBemFragment)mFragmentManager.findFragmentByTag(TAG_DLG_CADASTRO);
                if(fragment!= null)
                    mFragmentManager.beginTransaction().remove(fragment).commit();

                fragment = CadastroBemFragment.newInstance(0);
                fragment.show(mFragmentManager, TAG_DLG_CADASTRO);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });

        mLstBens = (ListView)findViewById(R.id.lstBens);
        mAdapter = new BemAdapter(getApplicationContext(), R.layout.item_bem, Global.getInstance(this).getBens());
        mLstBens.setAdapter(mAdapter);

        mLstBens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                final Bem bem = ListarBensActivity.this.mAdapter.getItem(posicao);
                int idPergunta = R.string.texto_confirma_exclusao;

                if(Global.getInstance(ListarBensActivity.this).IsBemAlugado(bem))
                    idPergunta = R.string.texto_confirma_devolucao;
                AcaoLongClickItem(idPergunta, bem);

                return true;
            }
        });

        mLstBens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Bem bem = ListarBensActivity.this.mAdapter.getItem(posicao);
                CadastroBemFragment fragment = (CadastroBemFragment)mFragmentManager.findFragmentByTag(TAG_DLG_CADASTRO);
                if(fragment!= null)
                    mFragmentManager.beginTransaction().remove(fragment).commit();

                fragment = CadastroBemFragment.newInstance(bem.getId());
                fragment.show(mFragmentManager, TAG_DLG_CADASTRO);
            }
        });

        Intent it = getIntent();
        if (it.getAction() != null && it.getAction().equals("br.com.falcone.locadora.android.ACAO_CUSTOMIZADA")) {
            long id = it.getLongExtra(AlugarBemFragment.BEM_ID, 0);
            /*Bem bem = null;
            for(Bem bemAtual:Global.getInstance().getBens()){
                if(bemAtual.getNome() == nomeBem)
                    bem = bemAtual;
            }*/
            Bem bem = Global.getInstance(this).getBemPorId(id);
            if(bem != null)
                AcaoLongClickItem(R.string.texto_confirma_devolucao, bem);
            else
                Toast.makeText(this, getString(R.string.texto_bem_inexistente), Toast.LENGTH_LONG).show();

        }
    }

    private void AcaoLongClickItem(final int idPergunta,final Bem bem) {

        AlertDialog pergunta = new AlertDialog.Builder(ListarBensActivity.this)
                .setMessage(idPergunta)
                .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Predicate<Bem> predicado = p-> p.getNome() == bem.getNome();
                        if(idPergunta == R.string.texto_confirma_devolucao && Global.getInstance(ListarBensActivity.this).IsBemAlugado(bem))
                        {
                            Integer idAlarme = Global.getInstance(ListarBensActivity.this).GetIdAlarme(bem);

                            Intent it = new Intent(ListarBensActivity.this, AlarmeDevolucao.class);
                            it.putExtra(AlugarBemFragment.BEM_ID, bem.getId());
                            AlarmManager alarmManager = (AlarmManager) ListarBensActivity.this.getSystemService(Context.ALARM_SERVICE);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(ListarBensActivity.this, idAlarme, it, PendingIntent.FLAG_CANCEL_CURRENT);
                            alarmManager.cancel(pendingIntent);
                            pendingIntent.cancel();

                            Global.getInstance(ListarBensActivity.this).getAlugueis().remove(idAlarme);

                        }
                        else if(idPergunta == R.string.texto_confirma_exclusao)
                            Global.getInstance(ListarBensActivity.this).ExcluirBem(bem);

                        ListarBensActivity.this.mAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create();

        pergunta.show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        /*Dialog view = dialog.getDialog();
        String nome = ((EditText)view.findViewById(R.id.tbNomeCadastro)).getText().toString();
        String genero = ((EditText)view.findViewById(R.id.tbGeneroCadastro)).getText().toString();
        Bem bem = new Bem();
        bem.setNome(nome);
        bem.setGenero(genero);
        if(!Global.getInstance().getBens().contains(bem)){
            Global.getInstance().getBens().add(bem);
        }
        else
        {
            Global.getInstance().getBens().get(Global.getInstance().getBens().indexOf(bem)).setGenero(genero);
        }*/
        //ListView lstBens = (ListView) ListarBensActivity.this.findViewById(R.id.lstBens);
        ListarBensActivity.this.mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
