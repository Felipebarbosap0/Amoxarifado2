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
    EditText Id, Nome,Quantidade;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView imagem;
    Uri uri;
    static Map<String,String> dados = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_item);
        getSupportActionBar().hide();


        ativador();
        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ie = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ie.setType("image/*");
                startActivityForResult(ie,0);
            }

        });



    }

    private void ativador() {
        Id = findViewById(R.id.editTextItemId);
        Nome = findViewById(R.id.editTextItemName);
        Quantidade = findViewById(R.id.editTextitemQuantidade);
        imagem = findViewById(R.id.imageView);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dados = new HashMap<>();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imagem.setImageDrawable(new BitmapDrawable(bitmap));
            }catch (IOException e){
                Log.e("imagemErro", String.valueOf(e));
            }

        }
    }


    public void bntConfirmar(View v) {

        if(Nome.getText().toString().isEmpty() ||
                Id.getText().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(),
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT).show();
        }else{


            String fileName = UUID.randomUUID().toString();
            StorageReference reference = FirebaseStorage.getInstance().getReference("/imagem/" + fileName);
            reference.putFile(uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    myRef = database.getReference( "/Item/" + Nome.getText().toString() +  "/");

                                    dados.put("ID",Id.getText().toString());
                                    dados.put("Quantidade",Quantidade.getText().toString());
                                    dados.put("Url", uri.toString());


                                    myRef.setValue(dados);
                                    Intent intent = new Intent(getApplicationContext(),HomeAdm.class);
                                    startActivity(intent);

                                }
                            });

                        }
                    });

        }





    }
}