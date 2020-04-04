package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.models.Gasto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarGasto extends AppCompatActivity {

    private String id_gasto;
    private EditText desc, valor,data;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Editar Gasto");

        inicilizarFireDataBase();



        desc  = (EditText) findViewById(R.id.txt_descricao_editar);
        valor = (EditText) findViewById(R.id.txt_valor_editar);
        data  = (EditText) findViewById(R.id.txtDataEditar);
        Bundle bundle = getIntent().getExtras();

        desc.setText(bundle.getString("descricaoEditar"));
        valor.setText(bundle.getString("valorEditar"));
        data.setText(bundle.getString("dataEditar"));
        id_gasto = bundle.getString("id_gastoEditar");
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(this, MeusGastos.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }

    public void editarGasto(View view)
    {
        if(isCampoVazio(desc.getText().toString()))
        {
            Toast.makeText(EditarGasto.this, "Digite uma descrição!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(valor.getText().toString()))
        {
            Toast.makeText(EditarGasto.this, "Digite um valor!", Toast.LENGTH_SHORT).show();
        }else if(isCampoVazio(data.getText().toString()))
        {
            Toast.makeText(EditarGasto.this, "Digite uma data!", Toast.LENGTH_SHORT).show();
        }else {


            Gasto gasto = new Gasto();
            gasto.setGasto_id(id_gasto);
            gasto.setUsuario_id(FirebaseAuth.getInstance().getUid());
            System.out.println("Setando gasto: " + id_gasto);
            gasto.setDescricao(desc.getText().toString());
            gasto.setValor(valor.getText().toString());
            gasto.setData(data.getText().toString());

            databaseReference.child("Gastos").child(gasto.getGasto_id()).setValue(gasto);
            Toast.makeText(EditarGasto.this, "Gasto alterado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent it = new Intent(EditarGasto.this, MeusGastos.class);
            startActivity(it);
        }
    }

    private void inicilizarFireDataBase()
    {
        FirebaseApp.initializeApp(EditarGasto.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private boolean isCampoVazio(String valor)
    {
        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }
}
