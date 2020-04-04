package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.connection.Conexao;
import com.example.myfinanceapp.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarUsuario extends AppCompatActivity
{

    private EditText txtUsuario,txtEmail,txtSenha,txtConfirmacao;
    private Button btnCadastrar;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastro");

        inicializarComponentes();
        eventsClick();
        inicilizarFireDataBase();
    }

    private void inicializarComponentes()
    {
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        txtUsuario = (EditText) findViewById(R.id.txt_usuario_cadastrar);
        txtEmail = (EditText) findViewById(R.id.txt_email_cadastro);
        txtSenha = (EditText) findViewById(R.id.txt_senha_cadastro);
        txtConfirmacao = (EditText) findViewById(R.id.txt_confirmacao_cadastro);
    }
    private void eventsClick()
    {
        btnCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
        String usuario = txtUsuario.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();
        String confirmacao = txtConfirmacao.getText().toString().trim();
        adicionarUsuario(usuario,email,senha,confirmacao);
            }
        });
    }

    private void adicionarUsuario(final String usuario, final String email, final String senha, final String confirmacao)
    {
        if(isCampoVazio(usuario))
        {
            Toast.makeText(CadastrarUsuario.this, "Digite o usuário!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(email))
        {
            Toast.makeText(CadastrarUsuario.this, "Digite o email!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(senha))
        {
            Toast.makeText(CadastrarUsuario.this, "Digite a senha!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(confirmacao))
        {
            Toast.makeText(CadastrarUsuario.this, "Digite a confirmação da senha!", Toast.LENGTH_SHORT).show();
        }else if(!senha.equals(confirmacao))
        {
            Toast.makeText(CadastrarUsuario.this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
        }else
        {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(CadastrarUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                    if(task.isSuccessful())
                    {

                        Usuario usuario = new Usuario();
                        usuario.setUsuario(txtUsuario.getText().toString().trim());
                        usuario.setEmail(txtEmail.getText().toString().trim());
                        usuario.setSenha(txtSenha.getText().toString().trim());

                        Toast.makeText(CadastrarUsuario.this, "Cadastro realizado com sucesso!",Toast.LENGTH_SHORT).show();
                        databaseReference.child("Usuario").push().setValue(usuario);

                        Intent it = new Intent(CadastrarUsuario.this, Login.class);
                        startActivity(it);
                    }else
                    {
                        Toast.makeText(CadastrarUsuario.this, "Deu erro aqui ô",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void inicilizarFireDataBase()
    {
        FirebaseApp.initializeApp(CadastrarUsuario.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private boolean isCampoVazio(String valor)
    {
        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Login.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        auth = Conexao.getReferenceAuth();
    }
}
