package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DadosUser extends AppCompatActivity {
    Map<String, String> dadosContatos; // Declaração de uma variável do tipo Map para armazenar os dados do contato
    TextView NomeUser, IdUser; // Declaração das variáveis TextView para exibir os dados do contato
    FirebaseAuth mAuth; // Variável para autenticação do Firebase
    FirebaseDatabase database; // Variável para o banco de dados do Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_user); // Define o layout da atividade
        getSupportActionBar().hide(); // Esconde a barra de ação da atividade

        iniciarComponentes(); // Chama o método para inicializar os componentes da tela

        mostrarContato(); // Chama o método para exibir os detalhes do contato na tela
    }

    private void mostrarContato() {
        NomeUser.setText(dadosContatos.get("Nome")); // Define o nome do contato no TextView correspondente
        IdUser.setText(dadosContatos.get("ID")); // Define o ID do contato no TextView correspondente

    }

    private void iniciarComponentes() {
        dadosContatos = HomeUser.dados; // Obtém os dados do contato da tela HomeUser
        NomeUser = findViewById(R.id.tvNome); // Obtém a referência do TextView para o nome do contato
        IdUser = findViewById(R.id.tvId); // Obtém a referência do TextView para o ID do contato
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void adicionar(View view) {
        // Método vazio, que provavelmente seria utilizado para adicionar algo
    }

    public void voltar(View view) {
        Intent i = new Intent(getApplicationContext(), HomeUser.class); // Cria uma intenção para voltar para a atividade HomeUser
        startActivity(i); // Inicia a atividade
    }
}
