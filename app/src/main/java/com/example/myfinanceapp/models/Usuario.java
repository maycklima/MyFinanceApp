package com.example.myfinanceapp.models;

public class Usuario
{

    private String usuario;
    private String email;
    private String senha;
    private String confirmacao;

    public Usuario(){
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(String confirmacao) {
        this.confirmacao = confirmacao;
    }

    @Override
    public String toString()
    {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", confirmacao='" + confirmacao + '\'' +
                '}';
    }
}
