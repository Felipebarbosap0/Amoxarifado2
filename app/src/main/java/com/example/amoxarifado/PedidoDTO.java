package com.example.amoxarifado;

// Definição da classe PedidoDTO, usada para mapear os dados dos pedidos do Firebase
// usada como um Data Transfer Object (DTO) para representar os dados dos pedidos que são lidos do Firebase
public class PedidoDTO {

    // Atributos correspondentes aos campos do pedido

    String idPedido;
    String idItem;
    String nomeItem;
    String quantidadeSolicitada;
    String usuarioSolicitante;

    // Construtor da classe para inicialização dos atributos
    public PedidoDTO(String idPedido, String idItem, String nomeItem, String quantidadeSolicitada, String usuarioSolicitante) {
        this.idPedido = idPedido;
        this.idItem = idItem;
        this.nomeItem = nomeItem;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.usuarioSolicitante = usuarioSolicitante;
    }

    // Construtor vazio necessário para a conversão dos dados do Firebase
    public PedidoDTO(){}

    // Métodos de acesso para os atributos
    //para que o Firebase possa realizar a conversão dos dados do banco de dados em objetos PedidoDTO.
    //os nomes dos atributos na classe correspondam exatamente aos nomes do banco de dados.

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(String quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(String usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }
}
