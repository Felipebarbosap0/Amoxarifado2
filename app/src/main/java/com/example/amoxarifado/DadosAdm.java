package com.example.amoxarifado;

// Importações necessárias para o funcionamento da classe

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

// Definição da classe DadosAdm que estende a classe AppCompatActivity
public class DadosAdm extends AppCompatActivity {
    // Declaração de variáveis
    Map<String, String> dadosContatos;
    TextView tvNome, tvQuantidade, tvId;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference deleteRef;

    // Método chamado quando a atividade é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_adm);
        getSupportActionBar().hide(); // Esconder a barra de ação

        iniciarComponentes(); // Inicializar componentes da interface
        mostrarContato(); // Exibir os detalhes do contato
    }

    // Exibe os detalhes do contato nos campos de texto
    private void mostrarContato() {
        tvNome.setText(dadosContatos.get("Nome"));
        tvQuantidade.setText(dadosContatos.get("Quantidade"));
        tvId.setText(dadosContatos.get("Id"));
    }

    // Inicializa os componentes da interface
    private void iniciarComponentes() {
        dadosContatos = HomeAdm.dados; // Obter dados do contato da classe HomeAdm
        tvNome = findViewById(R.id.tvNome);
        tvId = findViewById(R.id.tvId);
        tvQuantidade = findViewById(R.id.tvQuantidade);
        mAuth = FirebaseAuth.getInstance(); // Obter instância de autenticação do Firebase
        database = FirebaseDatabase.getInstance(); // Obter instância do banco de dados do Firebase
    }

    // Método para apagar um item do banco de dados
    public void apagar(View view) {
        deleteRef = database.getReference("User/" + mAuth.getUid()
                + "/Item/" + dadosContatos.get("Nome") + "/");

        // Remover o valor do banco de dados e adicionar um ouvinte para completar a operação
        deleteRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Item deletado com sucesso",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomeAdm.class);
                    startActivity(i); // Iniciar a atividade HomeAdm após a exclusão
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Item não foi deletado com sucesso",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para voltar para a atividade HomeAdm
    public void voltar(View view) {
        Intent i = new Intent(getApplicationContext(), HomeAdm.class);
        startActivity(i); // Iniciar a atividade HomeAdm
    }
}
