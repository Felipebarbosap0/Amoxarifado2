package com.example.amoxarifado;

public class ItemDTO {
    String nome;
    String ID;
    String Url;

    public ItemDTO(String nome, String ID, String url) {
        this.nome = nome;
        this.ID = ID;
        Url = url;
    }

    public ItemDTO(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }


}
