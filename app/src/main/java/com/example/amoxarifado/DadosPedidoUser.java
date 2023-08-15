package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class DadosPedidoUser extends AppCompatActivity {

    ImageView imagemItem; //Imagem
    Map<String, String> dadosItem; // Declaração de uma variável do tipo Map para armazenar os dados do item
    TextView nomeItem, idItem; // Declaração das variáveis TextView para exibir os dados do item
    FirebaseAuth mAuth; // Variável para autenticação do Firebase
    FirebaseDatabase database; // Variável para o banco de dados do Firebase
    DatabaseReference myRef;
    EditText quantidadeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_dados_user); // Define o layout da atividade
        getSupportActionBar().hide(); // Esconde a barra de ação da atividade
        iniciarComponentes(); // Chama o método para inicializar os componentes da tela
        exibirDadosItemSelecionado(); // Chama o método para exibir os detalhes do do item na tela
    }

    private void exibirDadosItemSelecionado() {
        nomeItem.setText(dadosItem.get("Nome")); // Define o nome do contato no TextView correspondente
        idItem.setText(dadosItem.get("ID")); // Define o ID do contato no TextView correspondente
    }

    private void iniciarComponentes() {
        dadosItem = HomeUser.dadosItem; // Obtém os dados do contato da tela HomeUser
        nomeItem = findViewById(R.id.nomeItem); // Obtém a referência do TextView para o nome do contato
        idItem =  findViewById(R.id.idItem);
        imagemItem = findViewById(R.id.imagemItem);
        Picasso.get()
                .load(HomeUser.dadosItem.get("Url")).into(imagemItem);
        quantidadeItem = (EditText) findViewById(R.id.quantidadeItem);
        mAuth = FirebaseAuth.getInstance(); // Inicializa o Firebase Authentication
        database = FirebaseDatabase.getInstance(); // Inicializa o Firebase Database
    }

    public void adicionar(View view) {

        HashMap<String,String> dados = new HashMap<>();
        // Preenche o mapa 'dados' com os valores do item
        dados.put("idItem", idItem.getText().toString());
        dados.put("nomeItem", nomeItem.getText().toString());
        dados.put("quantidadeSolicitada", quantidadeItem.getText().toString());
        dados.put("usuarioSolicitante", mAuth.getCurrentUser().getEmail());

        DatabaseReference pedidosRef = myRef.child("Pedido");
        DatabaseReference novoPedidoRef = pedidosRef.push();
        novoPedidoRef.setValue(dados);

        // Inicia uma nova atividade (HomeAdm)
        Intent intent = new Intent(getApplicationContext(), HomeUser.class);
        startActivity(intent);
    }

    public void voltar(View view) {
        Intent i = new Intent(getApplicationContext(), HomeUser.class); // Cria uma intenção para voltar para a atividade HomeUser
        startActivity(i); // Inicia a atividade
    }
}
