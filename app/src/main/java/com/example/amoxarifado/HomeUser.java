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

public class HomeUser extends AppCompatActivity {
    ListView list;
    List<String> nomes;
    List<String> quantidade;
    List<String> Id;

    String[] campos = {"ID", "Quantidade"};

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef,dadosRef;

    static Map<String, String> dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        getSupportActionBar().hide();
        ativar(); // Inicializa os componentes e variáveis

        pegarChaves(); // Inicia o processo de obtenção de dados do Firebase

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Adapyter adapter = new Adapyter(getApplicationContext(),nomes,quantidade,Id);
                list.setAdapter(adapter);
            }
        },5000); // Define um atraso de 5 segundos antes de exibir os dados na lista

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (int i = 0; i < nomes.size(); i++){
                    if (i == position){
                        // Define os dados selecionados para a próxima atividade
                        dados.put("Nome", nomes.get(position));
                        dados.put("ID", Id.get(position));
                        dados.put("Quantidade", quantidade.get(position));

                        Intent intent = new Intent(getApplicationContext(), DadosUser.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void pegarChaves() {
        myRef = database.getReference("Item/" );

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    String nome = key.getKey();
                    nomes.add(nome);

                    for (String campo : campos) {
                        dadosRef = database.getReference("Item/"
                                + nome + "/" + campo + "/");
                        pegarDados(campo); // Obtém os valores dos campos "ID" e "Quantidade"
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lida com erros de leitura dos dados
            }
        });
    }

    private void pegarDados(String campo){
        dadosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class);

                if (campo.equals("Quantidade")){
                    quantidade.add(valor); // Armazena as quantidades
                }else{
                    Id.add(valor); // Armazena os IDs
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lida com erros de leitura dos dados
            }
        });
    }

    private void ativar() {
        list = findViewById(R.id.list) ;
        nomes = new ArrayList<>();
        quantidade = new ArrayList<>();
        Id = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dados = new HashMap<>();
    }

    public void btnSair(View view){
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent); // Volta para a atividade principal
    }
}
