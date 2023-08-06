package com.example.amoxarifado;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DadosUser extends AppCompatActivity {
    Map<String, String> dadosContatos;
    TextView NomeUser, IdUser, QuantUser, QuantInser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference deleteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_adm) ;
        getSupportActionBar().hide();


        iniciarComponentes(); // Inicializa os componentes da tela

        mostrarContato(); // Exibe os detalhes do contato na tela
    }

    private void mostrarContato() {
        NomeUser.setText(dadosContatos.get("Nome")); // Define o nome do contato no TextView correspondente
        IdUser.setText(dadosContatos.get("ID")); // Define o email do contato no TextView correspondente
        QuantUser.setText(dadosContatos.get("Quantidade")); // Define o telefone do contato no TextView correspondente

    }

    private void iniciarComponentes() {

        dadosContatos = HomeAdm.dados; // Obtém os dados do contato da tela VContatos
        NomeUser = findViewById(R.id.tvNome); // Obtém a referência do TextView para o nome do contato
        QuantUser = findViewById(R.id.QuantUser);// Obtém a referência do TextView para o email do contato
        IdUser = findViewById(R.id.tvId);
        QuantInser = findViewById(R.id.QuantInser); // Obtém a referência do TextView para o telefone do contato
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }


    public void adcionar(View view){
        DatabaseReference updateRef = database.getReference("User/" + mAuth.getUid() + "/Item/" + dadosContatos.get("Nome") + "/");

        deleteRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Contato deletado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeUser.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Deu ruim ai",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public  void voltar(View view){
        Intent i = new Intent(getApplicationContext(), HomeAdm.class);
        startActivity(i);

    }
}