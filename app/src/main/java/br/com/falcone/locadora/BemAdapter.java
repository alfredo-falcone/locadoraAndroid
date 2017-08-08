package br.com.falcone.locadora;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alfredo on 22/07/2017.
 */

public class BemAdapter extends ArrayAdapter<Bem> {

    private int layoutId = 0;
    public BemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Bem> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bem bem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TextView txtGenero = convertView.findViewById(R.id.txtGenero);
        if(txtGenero != null)
            txtGenero.setText(bem.getGenero());
        TextView txtNome = convertView.findViewById(R.id.txtNome);
        if(txtNome == null) {
            if (convertView instanceof TextView)
                txtNome = (TextView) convertView;
        }
        if(txtNome != null) {
            txtNome.setText(bem.getNome());
        }

        ImageView img = convertView.findViewById(R.id.img);
        if(img != null){
            Picasso.with(getContext()).load(bem.getSite()).into(img);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(getContext());
        label.setTextColor(Color.BLACK);
        label.setText(getItem(position).getNome());

        return label;
    }
}
