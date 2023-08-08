package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity {

    // Declaração das variáveis para os campos de entrada e autenticação
    private EditText editTextUser, editTextSenha;
    private FirebaseAuth mAuth;
    CheckBox ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Chamada do método para inicializar componentes
        ativar();
    }

    // Método para inicializar os componentes
    private void ativar() {
        editTextUser = findViewById(R.id.editTextEmailLogin);
        editTextSenha = findViewById(R.id.editTextSenhaLogin);
        mAuth =  FirebaseAuth.getInstance();
        ms = findViewById(R.id.ms);
    }

    // Método para alterar a visibilidade da senha ao clicar na checkbox
    public void ms(View view){
        if (ms.isChecked()) {
            editTextSenha.setInputType(1); // 1 representa o tipo de entrada de texto "text"
        } else {
            editTextSenha.setInputType(129); // 129 representa o tipo de entrada de texto "password"
        }
    }

    // Método para executar o login ao clicar no botão
    public void btnLogin(View view) {
        if (editTextUser.getText().toString().isEmpty() ||
                editTextSenha.getText().toString().isEmpty()) {
            // Exibir uma mensagem de erro se os campos estiverem vazios
            Toast.makeText(getApplicationContext(),
                    "É necessário preencher todos os campos",
                    Toast.LENGTH_LONG).show();
        } else {
            // Iniciar o processo de autenticação com Firebase
            mAuth.signInWithEmailAndPassword(editTextUser.getText().toString(),
                            editTextSenha.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Verificar se o e-mail contém um '@'
                                if (!editTextUser.getText().toString().contains("@")) {
                                    // Exibir mensagem de erro para e-mail inválido
                                    Toast.makeText(getApplicationContext(),
                                            "E-mail inválido. Certifique-se de que o e-mail contenha um '@'",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    // Separar o e-mail em nome de usuário e domínio
                                    String[] parts = editTextUser.getText().toString().split("@");
                                    String dominio = parts[1];

                                    if (dominio.equals("senai.com")) {
                                        // Usuário administrador
                                        Log.d("user", "Administrador logado");
                                        Toast.makeText(getApplicationContext(),
                                                "Administrador logado",
                                                Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Main.this, HomeAdm.class);
                                        startActivity(i);
                                    } else {
                                        // Usuário comum
                                        Log.d("user", "Usuário logado");
                                        Toast.makeText(getApplicationContext(),
                                                "Usuário logado",
                                                Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Main.this, HomeUser.class);
                                        startActivity(i);
                                    }
                                }
                            } else {
                                // Tratamento de falha no login
                                if (editTextSenha.getText().toString().toCharArray().length < 6) {
                                    // Senha muito curta
                                    Toast.makeText(getApplicationContext(),
                                            "Senha não possui a quantidade necessária",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    String confir = "nao";
                                    for (char c : editTextUser.getText().toString().toCharArray()) {
                                        if (c == '@') {
                                            confir = "sim";
                                            break;
                                        }
                                    }

                                    if (confir.equals("sim")) {
                                        // E-mail válido, mas login inexistente
                                        Toast.makeText(getApplicationContext(),
                                                "Login inexistente",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        // E-mail inválido
                                        Toast.makeText(getApplicationContext(),
                                                "Email não existente",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    // Método para iniciar a atividade de cadastro ao clicar no botão
    public void btnCadastro(View view){
        Intent intent = new Intent(Main.this, Cadastro.class);
        startActivity(intent);
    }
}
