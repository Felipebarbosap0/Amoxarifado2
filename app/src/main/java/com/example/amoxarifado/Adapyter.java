package com.example.amoxarifado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class Adapyter extends BaseAdapter {

    // O contexto é necessário para inflar as views.
    Context context;

    // Listas que armazenam os dados que serão exibidos.
    List<String> Nome;

    List<String> Id;

    // Usado para inflar as views a partir do layout XML.
    LayoutInflater inflater;

    // Construtor do adaptador.
    public Adapyter(Context context, List<String> nome, List<String> id){
        this.context = context;
        this.Nome = nome;
        this.Id = id;


        // O LayoutInflater é usado para criar instâncias de layout a partir de um arquivo XML.
        inflater = LayoutInflater.from(context);
    }

    // Retorna o número de elementos na lista.
    @Override
    public int getCount() {
        return Nome.size();
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
        convertView = inflater.inflate(R.layout.adapyter, null);

        // Obtém referências para os elementos de texto na view.
        TextView nometv = convertView.findViewById(R.id.textViewNome);
        TextView idtv = convertView.findViewById(R.id.textViewId);

        // Define os valores dos elementos de texto com base nos dados da lista.
        nometv.setText(Nome.get(position));
        idtv.setText(Id.get(position));

        // Retorna a view completa para ser exibida na lista.
        return convertView;
    }
}
