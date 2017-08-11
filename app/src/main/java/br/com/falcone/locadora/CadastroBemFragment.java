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
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CadastroBemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CadastroBemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroBemFragment extends DialogFragment {

    private static final String ARG_NOME = "nome";


    private String mNome;
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

    public static CadastroBemFragment newInstance(String nome) {
        CadastroBemFragment fragment = new CadastroBemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOME, nome);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNome = getArguments().getString(ARG_NOME);
        }

    }


    private void GravarBem() {
        EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
        EditText tbGeneroCadastro = mView.findViewById(R.id.tbGeneroCadastro);
        this.mBem.setNome(tbNomeCadastro.getText().toString());
        this.mBem.setGenero(tbGeneroCadastro.getText().toString());
        if(!Global.getInstance().getBens().contains(this.mBem)){
            Global.getInstance().getBens().add(this.mBem);
        }
    }

    private void CarregarBemTela(View view)
    {

        EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
        EditText tbGeneroCadastro = mView.findViewById(R.id.tbGeneroCadastro);
        tbNomeCadastro.setText(this.mBem.getNome());
        tbGeneroCadastro.setText(this.mBem.getGenero());
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

            if (mNome != null) {
                mBem = Global.getInstance().getBens().get(Global.getInstance().getBens().indexOf(new Bem(mNome, null, null)));
                EditText tbNomeCadastro = mView.findViewById(R.id.tbNomeCadastro);
                tbNomeCadastro.setEnabled(false);

            }

            if(mBem == null) {

                mBem = new Bem(null, null, null);
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
                .setTitle((mNome != null) ? R.string.editar : R.string.novo)
                .setPositiveButton(R.string.texto_gravar,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                GravarBem();
                                //ListView lstBens = getActivity().findViewById(R.id.lstBens);
                                //((BemAdapter)lstBens.getAdapter()).notifyDataSetChanged();
                                ((DialogCadastroBemListener)getActivity()).onDialogPositiveClick(CadastroBemFragment.this);

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
