package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdm extends AppCompatActivity {
    // Declaração de variáveis
    ListView list;
    List<String> nomes;

    List<String> Id;

    String[] campos = {"ID", "Quantidade"};

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, dadosRef;

    static Map<String, String> dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_adm);
        getSupportActionBar().hide();
        ativar(); // Inicializa os elementos da interface

        pegarChaves(); // Obtém os dados do Firebase e popula as listas

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapyter adapter = new Adapyter(getApplicationContext(), nomes, Id);
                list.setAdapter(adapter);
            }
        }, 5000); // Aguarda 5 segundos e depois define o adapter da lista

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (int i = 0; i < nomes.size(); i++) {
                    if (i == position) {
                        // Preenche o mapa 'dados' com informações do item selecionado
                        dados.put("Nome", nomes.get(position));
                        dados.put("ID", Id.get(position));

                        // Abre a tela de DadosAdm com as informações preenchidas
                        Intent intent = new Intent(getApplicationContext(), DadosAdm.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // Método para obter as chaves (nomes dos itens) do Firebase
    private void pegarChaves() {
        myRef = database.getReference("Item/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    String nome = key.getKey();
                    nomes.add(nome);

                    // Para cada nome de item, obtém os dados de cada campo (ID e Quantidade)
                    for (String campo : campos) {
                        dadosRef = database.getReference("Item/"
                                + nome + "/" + campo + "/");
                        pegarDados(campo); // Obtém os valores dos campos
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tratamento de erro, se necessário
            }
        });
    }

    // Método para obter os valores dos campos (ID e Quantidade) de cada item
    private void pegarDados(String campo) {
        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class);

                if (campo.equals("Id")) {
                    Id.add(valor); // Adiciona o ID à lista
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tratamento de erro, se necessário
            }
        });
    }

    // Método para inicializar elementos da interface e Firebase
    private void ativar() {
        list = findViewById(R.id.list);
        nomes = new ArrayList<>();
        Id = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dados = new HashMap<>();
    }

    // Método para lidar com o clique do botão "Adicionar Item"
    public void btnItem(View view) {
        Intent intent = new Intent(HomeAdm.this, CriacaoItem.class);
        startActivity(intent);
    }

    // Método para lidar com o clique do botão "Sair"
    public void btnSair(View view) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
    }
}
