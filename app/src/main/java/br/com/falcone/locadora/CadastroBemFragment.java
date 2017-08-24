package br.com.falcone.locadora;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.falcone.locadora.model.Bem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CadastroBemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CadastroBemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroBemFragment extends DialogFragment {

    private static final String ARG_ID = "id";


    private long mId;
    private Bem mBem;
    private DialogCadastroBemListener mListener;
    private View mView;

    public CadastroBemFragment() {
        // Required empty public constructor
    }

    public interface DialogCadastroBemListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public static CadastroBemFragment newInstance(long id) {
        CadastroBemFragment fragment = new CadastroBemFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getLong(ARG_ID);
        }
        else
            mId = 0;

    }


    private boolean GravarBem() {
        boolean retorno = false;
        EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
        EditText tbGeneroCadastro = mView.findViewById(R.id.tbGeneroCadastro);
        EditText tbPrecoCadastro = mView.findViewById(R.id.tbPrecoCadastro);

        if(tbNomeCadastro.getText().length() == 0 ||
                tbGeneroCadastro.getText().length() == 0 || tbPrecoCadastro.getText().length() == 0){
            Toast.makeText(getContext(), R.string.toast_dados_obrigatorios, Toast.LENGTH_LONG).show();

        }
        else {
            this.mBem.setNome(tbNomeCadastro.getText().toString());
            this.mBem.setGenero(tbGeneroCadastro.getText().toString());
            this.mBem.setPrecoHora(Double.parseDouble(tbPrecoCadastro.getText().toString()));

            if (this.mId == 0) {
                Global.getInstance(getContext()).InserirBem(this.mBem);
            }
            else{
                Global.getInstance(getContext()).AtualizarBem(this.mBem);
            }

            retorno = true;
        }
        return retorno;
    }

    private void CarregarBemTela(View view)
    {
        EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
        EditText tbGeneroCadastro = mView.findViewById(R.id.tbGeneroCadastro);
        EditText tbPrecoCadastro = mView.findViewById(R.id.tbPrecoCadastro);
        tbNomeCadastro.setText(this.mBem.getNome());
        tbGeneroCadastro.setText(this.mBem.getGenero());
        tbPrecoCadastro.setText(this.mBem.getPrecoHora().toString());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_cadastro_bem, container, false);

        //EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
        //EditText tbGeneroCadastro = mView.findViewById(R.id.tbGeneroCadastro);
        //EditText tbNome = container.findViewById(R.id.tbNomeCadastro);
        //tbNomeCadastro.setText("teste");
        if(mBem == null) {

            if (mId != 0) {
                mBem = Global.getInstance(getContext()).getBemPorId(mId);
                //EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
                //tbNomeCadastro.setEnabled(false);

            }

            if(mBem == null) {

                mBem = new Bem(mId, null, null, 0.0);
            }
        }
        CarregarBemTela(mView);
        return mView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int title = getArguments().getInt("title");
        this.mView = getActivity().getLayoutInflater().inflate(R.layout.fragment_cadastro_bem, null);
        AlertDialog dlg =  new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.alert_dialog_icon)
                .setTitle((mId != 0) ? R.string.editar : R.string.novo)
                .setPositiveButton(R.string.texto_gravar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {


                            }
                        }
                )
                .setNegativeButton(R.string.texto_cancelar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((DialogCadastroBemListener)getActivity()).onDialogNegativeClick(CadastroBemFragment.this);

                            }
                        }
                ).setView(mView).create();

        return dlg;
    }

    @Override
    public void onStart(){
        super.onStart();
        AlertDialog dlg = (AlertDialog) getDialog();
        if(dlg != null) {

            dlg.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (GravarBem()) {
                        //ListView lstBens = getActivity().findViewById(R.id.lstBens);
                        //((BemAdapter)lstBens.getAdapter()).notifyDataSetChanged();
                        ((DialogCadastroBemListener) getActivity()).onDialogPositiveClick(CadastroBemFragment.this);
                        dismiss();
                    }
                }
            });
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogCadastroBemListener) {
            mListener = (DialogCadastroBemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
