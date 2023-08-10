package com.example.amoxarifado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PedidosAdapter extends BaseAdapter {

    private final List<PedidoDTO> pedidos;
    // O contexto é necessário para inflar as views.
    Context context;

    // Listas que armazenam os dados que serão exibidos.
    List<String> nomes;

    List<String> ids;

    // Usado para inflar as views a partir do layout XML.
    LayoutInflater inflater;

    // Construtor do adaptador.
    public PedidosAdapter(Context context, List<PedidoDTO> pedidos){
        this.context = context;
        this.pedidos = pedidos;

        // O LayoutInflater é usado para criar instâncias de layout a partir de um arquivo XML.
        inflater = LayoutInflater.from(context);
    }

    // Retorna o número de elementos na lista.
    @Override
    public int getCount() {
        return pedidos.size();
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
        convertView = inflater.inflate(R.layout.pedidos_adapter, null);

        // Obtém referências para os elementos de texto na view.
        TextView nomeTV = convertView.findViewById(R.id.textViewNome);
        TextView idTV = convertView.findViewById(R.id.textViewId);
        TextView quantidadeSolicitadaTV = convertView.findViewById(R.id.textViewQuantidadeSolicitada);
        TextView solicitanteTV = convertView.findViewById(R.id.textViewUsuarioSolicitante);

        // Define os valores dos elementos de texto com base nos dados da lista.
        nomeTV.setText(pedidos.get(position).nomeItem);
        idTV.setText(pedidos.get(position).idItem);
        quantidadeSolicitadaTV.setText(pedidos.get(position).quantidadeSolicitada);
        solicitanteTV.setText(pedidos.get(position).usuarioSolicitante);

        // Retorna a view completa para ser exibida na lista.
        return convertView;
    }
}
