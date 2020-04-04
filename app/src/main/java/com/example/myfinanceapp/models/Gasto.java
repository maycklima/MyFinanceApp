package com.example.myfinanceapp.models;

public class Gasto
{
    private String usuario_id;
    private String id_gasto;
    private String descricao;
    private String valor;
    private String data;

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
    {
        this.valor = valor;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getGasto_id()
    {
        return id_gasto;
    }

    public void setGasto_id(String gasto_id) {
        this.id_gasto = gasto_id;
    }

    @Override
    public String toString()
    {
        return  descricao + "\nR$ " + valor  + ",00";
    }


}
