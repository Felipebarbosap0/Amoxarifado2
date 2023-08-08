package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersRef;

    static Map<String,String> dados = new HashMap<>(); // Cria um mapa para armazenar dados
    private EditText editTextNome, editTextEmail, editTextSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide(); // Esconde a barra de ação

        ativar(); // Chama o método "ativar"
    }

    private void ativar() {
        editTextNome = findViewById(R.id.editTextNomeCadastro);
        editTextEmail = findViewById(R.id.editTextEmailCadastro);
        editTextSenha = findViewById(R.id.editTextSenhaCadastro);
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
        dados = new HashMap<>(); // Reinicializa o mapa de dados
    }

    public void btnCadastrar(View view) {
        // Verifica se os campos estão vazios
        if (editTextNome.getText().toString().isEmpty() ||
                editTextEmail.getText().toString().isEmpty() ||
                editTextSenha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Recebe o email e senha digitados
            String email = editTextEmail.getText().toString();
            String senha = editTextSenha.getText().toString();

            // Verifica se o email é válido
            if (!email.contains("@")) {
                Toast.makeText(getApplicationContext(),
                        "E-mail inválido. Certifique-se de que o e-mail contenha um '@'",
                        Toast.LENGTH_LONG).show();
            } else {
                String[] parts = email.split("@"); // Divide o email em duas partes
                String dominio = parts[1]; // Pega a parte do domínio

                // Verifica se o domínio é "senai.com"
                if (dominio.equals("senai.com")) {
                    Toast.makeText(getApplicationContext(),
                            "E-mail corporativo. Usuários com email corporativo devem ser cadastrados pela própria organização.",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Cria o usuário no Firebase Authentication
                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Exibe mensagem de cadastro bem-sucedido
                                    Toast.makeText(getApplicationContext(),
                                            "Cadastro Realizado com sucesso!!!",
                                            Toast.LENGTH_SHORT).show();

                                    // Cria uma referência no Firebase Database para o usuário cadastrado
                                    usersRef = database.getReference("User/" + mAuth.getUid() + "/");
                                    dados.put("Nome", editTextNome.getText().toString().trim());
                                    dados.put("Email", editTextEmail.getText().toString().trim());
                                    dados.put("Senha", editTextSenha.getText().toString().trim());

                                    usersRef.setValue(dados); // Define os dados do usuário no Database
                                    Toast.makeText(getApplicationContext(),
                                            "Cadastro Realizado!!!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Main.class);
                                    startActivity(intent); // Inicia a atividade "Main"
                                }
                            });
                }
            }
        }
    }
}
