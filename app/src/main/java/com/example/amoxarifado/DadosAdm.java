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

public class DadosAdm extends AppCompatActivity {
    Map<String, String> dadosContatos;
    TextView tvNome, tvQuantidade, tvId;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference deleteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_adm);
        getSupportActionBar().hide();


        iniciarComponentes();

        mostrarContato();
    }

    private void mostrarContato() {
        tvNome.setText(dadosContatos.get("Nome"));
        tvQuantidade.setText(dadosContatos.get("Quantidade"));
        tvId.setText(dadosContatos.get("Id"));
    }

    private void iniciarComponentes() {

        dadosContatos = HomeAdm.dados;
        tvNome = findViewById(R.id.tvNome);
        tvId = findViewById(R.id.tvId);
        tvQuantidade = findViewById(R.id.tvQuantidade);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }



    public void apagar(View view){
        deleteRef = database.getReference("User/" + mAuth.getUid()
                + "/Item/" + dadosContatos.get("Nome") + "/");

        deleteRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Item deletado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeAdm.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Item não foi deletado com sucesso",
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
