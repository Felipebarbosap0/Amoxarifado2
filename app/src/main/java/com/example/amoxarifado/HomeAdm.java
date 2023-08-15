package com.example.amoxarifado;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeAdm extends AppCompatActivity {
    ListView itensListView;
    ArrayList<ItemDTO> itens;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef,dadosRef;
    static Map<String, String> dadosItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_adm);
        setTitle("Itens Solicitáveis");
        ativar(); // Inicializa os componentes e variáveis
        recuperarItens(); //resgata os itens do Firebase
        //pegarChaves(); // Inicia o processo de obtenção de dados do Firebase

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ItemAdapter adapter = new ItemAdapter(getApplicationContext(), itens);
                itensListView.setAdapter(adapter);
            }
        },2000); // Define um atraso de 5 segundos antes de exibir os dados na lista

        itensListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Define os dados selecionados para a próxima atividade
                dadosItem.put("Nome", itens.get(position).nome);
                dadosItem.put("ID", itens.get(position).ID);
                dadosItem.put("Url", itens.get(position).Url);

                Intent intent = new Intent(getApplicationContext(), DadosPedidoUser.class);
                startActivity(intent);
            }
        });
    }

    // Método para inicializar variáveis e objetos
    private void ativar() {
        itensListView = findViewById(R.id.listItens) ;
        itens = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dadosItem = new HashMap<>();
    }

    public void recuperarItens(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Item/");
        itens = new ArrayList<ItemDTO>();

        // Listener para capturar quando um novo pedido for adicionado ao nó "/Item/"
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // Converter o DataSnapshot para um objeto PedidoDTO
                ItemDTO novoPedido = dataSnapshot.getValue(ItemDTO.class);
                novoPedido.nome = dataSnapshot.getKey();
                itens.add(novoPedido);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Este método é chamado quando os detalhes de um pedido existente forem alterados
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Este método é chamado quando um pedido for removido

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Este método é chamado quando a ordem dos pedidos for alterada

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Este método é chamado em caso de erro na leitura dos dados

            }
        };

        ref.addChildEventListener(childEventListener);

    }

    // Método para lidar com o clique do botão "Adicionar Item"
    public void btnItem(View view) {
        Intent intent = new Intent(HomeAdm.this, CriacaoItem.class);
        startActivity(intent);
    }

    // Método para voltar para a atividade principal
    public void btnSair(View view){
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent); // Volta para a atividade principal
    }

    // Método para abrir a atividade de listagem de pedidos
    public void visualizarPedidos(View view){
        Intent intent = new Intent(getApplicationContext(), ListagemPedidos.class);
        startActivity(intent);
    }
}
