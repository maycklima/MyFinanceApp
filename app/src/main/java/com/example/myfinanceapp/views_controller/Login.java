package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.connection.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity
{
    private EditText txtUsuario,txtEmail,txtSenha,txtConfirmacao;
    private Button btnLogin;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        auth = FirebaseAuth.getInstance();
    }

    public void fazerLogin(View view)
    {
        EditText usuario = (EditText) findViewById(R.id.txt_usuario_login);
        EditText senha = (EditText) findViewById(R.id.txt_senha_login);
        final String usuario2 = usuario.getText().toString().trim();
        final String senha2 = senha.getText().toString().trim();

         if(isCampoVazio(usuario2))
         {
                Toast.makeText(Login.this, "Digite o email!", Toast.LENGTH_SHORT).show();
         }else if(isCampoVazio(senha2))
         {
            Toast.makeText(Login.this, "Digite a senha!", Toast.LENGTH_SHORT).show();
         }else
         {
             auth.signInWithEmailAndPassword(usuario2,senha2).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                     if(task.isSuccessful())
                     {
                         Toast.makeText(Login.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                         Intent it = new Intent(Login.this, MeusGastos.class);
                         startActivity(it);
                     }else{
                         Toast.makeText(Login.this, "Email ou senha inválido!", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
    }

    public void cadastrarUsuario(View view)
    {
        Intent it = new Intent(Login.this, CadastrarUsuario.class);
        //it.putExtra("usuario", usuario.getText().toString());
        startActivity(it);
    }

    private boolean isCampoVazio(String valor)
    {
        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        auth = Conexao.getReferenceAuth();
    }
}
