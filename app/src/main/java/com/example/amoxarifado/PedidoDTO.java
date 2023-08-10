package com.example.amoxarifado;

public class PedidoDTO {
    String idPedido;
    String idItem;
    String nomeItem;
    String quantidadeSolicitada;
    String usuarioSolicitante;

    public PedidoDTO(String idPedido, String idItem, String nomeItem, String quantidadeSolicitada, String usuarioSolicitante) {
        this.idPedido = idPedido;
        this.idItem = idItem;
        this.nomeItem = nomeItem;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public PedidoDTO(){}

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
