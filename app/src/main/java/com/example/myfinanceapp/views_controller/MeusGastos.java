package com.example.myfinanceapp.views_controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myfinanceapp.R;
import com.example.myfinanceapp.models.Gasto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeusGastos extends AppCompatActivity
{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView lista_gastos_view;
    private List<Gasto> lista_gastos = new ArrayList<Gasto>();
    private ArrayAdapter<Gasto> arrayAdapterGasto;
    private static Gasto gastoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_gastos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent it = new Intent(MeusGastos.this, AdicionarGasto.class);
                //it.putExtra("usuario", usuario.getText().toString());
                startActivity(it);
            }
        });

        inicilizarFireDataBase();
        inicializarComponentes();
        eventDataBase();

        lista_gastos_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                gastoSelecionado = (Gasto)parent.getItemAtPosition(position);
                Intent it = new Intent(MeusGastos.this, DetalhesGastos.class);
                Bundle params = new Bundle();

                params.putString("gasto_id" ,  gastoSelecionado.getGasto_id());
                System.out.println("Meus gastos para Detalhes - " + gastoSelecionado.getGasto_id());
                params.putString("descricao",  gastoSelecionado.getDescricao());
                params.putString("valor",      gastoSelecionado.getValor());
                params.putString("data",       gastoSelecionado.getData());

                it.putExtras(params);
                startActivity(it);

            }
        });
    }

    private void inicilizarFireDataBase()
    {
        FirebaseApp.initializeApp(MeusGastos.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void inicializarComponentes()
    {
        lista_gastos_view = (ListView) findViewById(R.id.lista_gastos);
    }

    public void eventDataBase()
    {
        databaseReference.child("Gastos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_gastos.clear();
                String id1 = FirebaseAuth.getInstance().getUid();
                String id2;

                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Gasto gasto = objSnapshot.getValue(Gasto.class);
                    id2 = gasto.getUsuario_id();
                    if(id1.equals(id2)){
                        lista_gastos.add(gasto);
                    }
                }
                arrayAdapterGasto = new ArrayAdapter<Gasto>(MeusGastos.this,android.R.layout.simple_list_item_1,lista_gastos);
                lista_gastos_view.setAdapter(arrayAdapterGasto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
