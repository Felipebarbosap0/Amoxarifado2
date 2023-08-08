package com.example.amoxarifado;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CriacaoItem extends AppCompatActivity {
    // Declaração de variáveis para os elementos da interface
    EditText Id, Nome;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView imagem;
    Uri uri;
    static Map<String,String> dados = new HashMap<>(); // Mapa para armazenar os dados do item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_item);
        getSupportActionBar().hide();

        // Chama o método para inicializar os elementos da interface
        ativador();

        // Configura um OnClickListener para o ImageView 'imagem'
        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma intenção para selecionar uma imagem da galeria
                Intent ie = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ie.setType("image/*");
                startActivityForResult(ie, 0);
            }
        });
    }

    // Método para inicializar os elementos da interface
    private void ativador() {
        Id = findViewById(R.id.editTextItemId);
        Nome = findViewById(R.id.editTextItemName);

        imagem = findViewById(R.id.imageView);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dados = new HashMap<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            // Obtém a Uri da imagem selecionada
            uri = data.getData();

            try {
                // Converte a Uri da imagem para um Bitmap e o define no ImageView 'imagem'
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imagem.setImageDrawable(new BitmapDrawable(bitmap));
            } catch (IOException e) {
                Log.e("imagemErro", String.valueOf(e));
            }
        }
    }

    public void bntConfirmar(View v) {
        if(Nome.getText().toString().isEmpty() || Id.getText().toString().isEmpty()) {
            // Exibe um Toast se algum campo estiver vazio
            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Gera um nome único para a imagem no armazenamento Firebase
            String fileName = UUID.randomUUID().toString();
            // Obtém a referência para o local onde a imagem será armazenada no Firebase Storage
            StorageReference reference = FirebaseStorage.getInstance().getReference("/imagem/" + fileName);

            // Faz o upload da imagem para o Firebase Storage
            reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Obtém uma referência para o local onde os dados do item serão armazenados no Firebase Realtime Database
                            myRef = database.getReference("/Item/" + Nome.getText().toString() + "/");

                            // Preenche o mapa 'dados' com os valores do item
                            dados.put("ID", Id.getText().toString());
                            dados.put("Url", uri.toString());

                            // Define os valores no Firebase Realtime Database
                            myRef.setValue(dados);

                            // Inicia uma nova atividade (HomeAdm)
                            Intent intent = new Intent(getApplicationContext(), HomeAdm.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}
