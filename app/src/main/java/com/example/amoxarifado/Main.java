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

    private EditText editTextUser, editTextSenha;
    private FirebaseAuth mAuth;
    CheckBox ms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ativar();
    }

    private void ativar() {
        editTextUser = findViewById(R.id.editTextEmailLogin);
        editTextSenha = findViewById(R.id.editTextSenhaLogin);
        mAuth =  FirebaseAuth.getInstance();
        ms = findViewById(R.id.ms);

    }
    public void  ms(View view){
        if (ms.isChecked()) {
            editTextSenha.setInputType(1);
        } else {
            editTextSenha.setInputType(129);
        }
    }
    public void btnLogin(View view) {
        if (editTextUser.getText().toString().isEmpty() ||
                editTextSenha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "É necessário preencher todos os campos",
                    Toast.LENGTH_LONG).show();
        }
        else {
            // estabelecendo variáveis
            mAuth.signInWithEmailAndPassword(editTextUser.getText().toString(),
                            editTextSenha.getText().toString())

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Verifica se o e-mail contém um '@'
                                if (!editTextUser.getText().toString().contains("@")) {
                                    Toast.makeText(getApplicationContext(),
                                            "E-mail inválido. Certifique-se de que o e-mail contenha um '@'",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    // Separa o e-mail em duas partes: nome de usuário e domínio
                                    String[] parts = editTextUser.getText().toString().split("@");
                                    String dominio = parts[1];

                                    // Verifica o tipo de usuário
                                    if (dominio.equals("senai.com")) {

                                        // Usuário é do tipo 'adm'
                                        Log.d("user", "Administrador logado");
                                        Toast.makeText(getApplicationContext(),
                                                "Administrador logado",
                                                Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Main.this, HomeAdm.class);
                                        startActivity(i);
                                    } else {
                                        // Usuário é do tipo 'usuário'
                                        Log.d("user", "Usuário logado");
                                        Toast.makeText(getApplicationContext(),
                                                "Usuário logado",
                                                Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Main.this, HomeUser.class);
                                        startActivity(i);
                                    }
                                }

                            } else {

                                //verificação de entrada

                                if (editTextSenha.getText().toString().toCharArray().length < 6) {

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

                                    if (confir == "sim") {

                                        Toast.makeText(getApplicationContext(),
                                                "Login inexistente",
                                                Toast.LENGTH_LONG).show();
                                    } else {

                                        Toast.makeText(getApplicationContext(),
                                                "Email não existente",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                                //sucesso no login



                            }
                        }
                    });
        }
    }




    public void btnCadastro(View view){
        Intent intent = new Intent(Main.this, Cadastro.class);
        startActivity(intent);

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        Log.d("user", String.valueOf(currentUser));
//
//        if (currentUser != null) {
//             Se já houver um usuário logado, redireciona para a tela Home
//            Intent intent = new Intent(getApplicationContext(), HomeAdm.class);
//            startActivity(intent);
//        }
//    }

}