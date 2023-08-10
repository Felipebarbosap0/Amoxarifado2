package com.example.amoxarifado;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListagemPedidos extends AppCompatActivity {
    ListView listPedidos;
    List<String> nomes;
    List<String> Id;
    String[] campos = {"ID", "Quantidade"};

    List<PedidoDTO> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedidos);
        setTitle("Meu Pedidos");
        listPedidos = findViewById(R.id.listItens);

        recuperarPedidos();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PedidosAdapter adapter = new PedidosAdapter(getApplicationContext(), pedidos);
                listPedidos.setAdapter(adapter);
            }
        },500); // Define um atraso de 5 segundos antes de exibir os dados na lista


    }

    public void recuperarPedidos(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/Pedido/");
        pedidos = new ArrayList<PedidoDTO>();

        // Listener para capturar quando um novo pedido for adicionado ao nó "/Pedido/"
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // Converter o DataSnapshot para um objeto PedidoDTO
                PedidoDTO novoPedido = dataSnapshot.getValue(PedidoDTO.class);
                novoPedido.idPedido = dataSnapshot.getKey();
                pedidos.add(novoPedido);
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

        String emailUsuarioLogado = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String[] parts = emailUsuarioLogado.split("@"); // Divide o email em duas partes
        String dominio = parts[1]; // Pega a parte do domínio

        // Verifica se o usuário é admin, se for busca todos os pedidos, do contrário (else) busca apenas os pedidos do realizados pelo usuário logado
        if (dominio.equals("senai.com")) {
            ref.orderByChild("usuarioSolicitante").addChildEventListener(childEventListener);
        }else{
            ref.orderByChild("usuarioSolicitante").equalTo(emailUsuarioLogado).addChildEventListener(childEventListener);
        }

    }

    public void voltar(View view){
        super.onBackPressed();
    }

}
