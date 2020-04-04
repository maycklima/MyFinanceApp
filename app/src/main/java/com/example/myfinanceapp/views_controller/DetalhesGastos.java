package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.models.Gasto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalhesGastos extends AppCompatActivity
{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Gasto g = new Gasto();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_gastos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Detalhes");

        inicilizarFireDataBase();

        TextView desc = (TextView) findViewById(R.id.txt_show_descricao);
        TextView valor = (TextView) findViewById(R.id.txt_show_valor);
        TextView data = (TextView) findViewById(R.id.txt_show_data);

        Bundle bundle = getIntent().getExtras();
        Gasto g = new Gasto();


        desc.setText(bundle.getString("descricao"));
        valor.setText("R$ " + bundle.getString("valor") + ",00");
        data.setText(bundle.getString("data"));

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
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
        Bundle bundle = getIntent().getExtras();


        bundle.putString("id_gastoEditar", bundle.getString("gasto_id"));
        g.setGasto_id(bundle.getString("gasto_id"));
        System.out.println("Passando para editar "+ bundle.getString("gasto_id"));
        bundle.putString("descricaoEditar", bundle.getString("descricao"));
        bundle.putString("valorEditar", bundle.getString("valor"));
        bundle.putString("dataEditar", bundle.getString("data"));
        Intent it = new Intent(DetalhesGastos.this, EditarGasto.class);
        it.putExtras(bundle);
        startActivity(it);
    }

    public void removerGasto(View view)
    {

        Bundle bundle = getIntent().getExtras();
        String gasto = bundle.getString("gasto_id");

        databaseReference.child("Gastos").child(gasto).removeValue();
        Toast.makeText(DetalhesGastos.this, "Gasto removido com sucesso!",Toast.LENGTH_SHORT).show();
        Intent it = new Intent(DetalhesGastos.this, MeusGastos.class);
        startActivity(it);
    }

    private void inicilizarFireDataBase()
    {
        FirebaseApp.initializeApp(DetalhesGastos.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
