package com.example.amoxarifado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    // O contexto é necessário para inflar as views.
    Context context;

    // Usado para inflar as views a partir do layout XML.
    LayoutInflater inflater;
    List<ItemDTO> itens;

    // Construtor do adaptador.
    public ItemAdapter(Context context, List<ItemDTO> itens){
        this.context = context;
        this.itens = itens;


        // O LayoutInflater é usado para criar instâncias de layout a partir de um arquivo XML.
        inflater = LayoutInflater.from(context);
    }

    // Retorna o número de elementos na lista.
    @Override
    public int getCount() {
        return itens.size();
    }

    // Retorna o item na posição especificada. Não é utilizado aqui.
    @Override
    public Object getItem(int i) {
        return null;
    }

    // Retorna o ID do item na posição especificada. Não é utilizado aqui.
    @Override
    public long getItemId(int i) {
        return 0;
    }

    // Retorna a view que será exibida na posição especificada.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla a view a partir do layout XML.
        convertView = inflater.inflate(R.layout.item_adapter, null);

        // Obtém referências para os elementos de texto na view.
        TextView nometv = convertView.findViewById(R.id.textViewNome);
        TextView idtv = convertView.findViewById(R.id.textViewId);
        ImageView imagemItem = convertView.findViewById(R.id.imagemItem);

        // Define os valores dos elementos de texto com base nos dados da lista.
        nometv.setText(itens.get(position).nome);
        idtv.setText(itens.get(position).ID);
        Picasso.get()
                .load(itens.get(position).Url).into(imagemItem);

        // Retorna a view completa para ser exibida na lista.
        return convertView;
    }
}
