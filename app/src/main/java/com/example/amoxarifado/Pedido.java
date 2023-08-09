package com.example.amoxarifado;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Pedido extends AppCompatActivity {

    ListView listPedido;
    List<String> nomes;

    List<String> Id;

    String[] campos = {"ID", "Quantidade"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapyter adapter = new Adapyter(getApplicationContext(),nomes,Id);
                listPedido.setAdapter(adapter);
            }
        },5000); // Define um atraso de 5 segundos antes de exibir os dados na lista


    }


    }
