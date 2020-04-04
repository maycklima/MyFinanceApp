package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.models.Gasto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AdicionarGasto extends AppCompatActivity
{

    private EditText txtDescricao,txtValor,txtData,sppiner;
    private Button btnAdicionar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Adicionar Gasto");

        inicializarComponentes();
        eventsClick();
        inicilizarFireDataBase();
    }

    private void eventsClick()
    {
        btnAdicionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adicionarGasto();
            }
        });
    }

    private void inicializarComponentes()
    {
        btnAdicionar = (Button) findViewById(R.id.btn_adicionar_gasto);
        txtDescricao = (EditText) findViewById(R.id.txt_descricao);
        txtValor = (EditText) findViewById(R.id.txt_valor);
        txtData = (EditText) findViewById(R.id.txt_data);

    }

    private void adicionarGasto()
    {
        if(isCampoVazio(txtDescricao.getText().toString()))
        {
            Toast.makeText(AdicionarGasto.this, "Digite uma descrição!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(txtValor.getText().toString()))
        {
            Toast.makeText(AdicionarGasto.this, "Digite um valor!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(txtData.getText().toString()))
        {
            Toast.makeText(AdicionarGasto.this, "Digite uma data!", Toast.LENGTH_SHORT).show();
        }else
            {
                String usuario_id = FirebaseAuth.getInstance().getUid();

                Gasto gasto = new Gasto();
                String id_gasto = UUID.randomUUID().toString();
                gasto.setGasto_id(id_gasto);
                gasto.setDescricao(txtDescricao.getText().toString());
                gasto.setValor(txtValor.getText().toString());
                gasto.setData(txtData.getText().toString());
                gasto.setUsuario_id(usuario_id);

                databaseReference.child("Gastos").child(id_gasto).setValue(gasto);
                Toast.makeText(AdicionarGasto.this, "Gasto cadastrado com sucesso!",Toast.LENGTH_SHORT).show();

                Intent it = new Intent(AdicionarGasto.this, MeusGastos.class);
                startActivity(it);
            }
       }

    private void inicilizarFireDataBase()
    {
        FirebaseApp.initializeApp(AdicionarGasto.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private boolean isCampoVazio(String valor)
    {
        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MeusGastos.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}
