package com.aquiles.listadetarefas.activity;

import android.content.Intent;
import android.os.Bundle;

import com.aquiles.listadetarefas.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aquiles.listadetarefas.adapter.TarefaAdapter;
import com.aquiles.listadetarefas.databinding.ActivityMainBinding;
import com.aquiles.listadetarefas.model.Tarefa;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView listaDeTarefas;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<Tarefa>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // confifurar recycler
        listaDeTarefas = findViewById(R.id.listaDeTarefas);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaDeTarefas();
    }

    public void carregarListaDeTarefas() {
        // listar tarefas
        Tarefa t1 = new Tarefa();
        t1.setNomeTarefa("trabalhar");
        listaTarefas.add(t1);

        Tarefa t2 = new Tarefa();
        t2.setNomeTarefa("ir na budega");
        listaTarefas.add(t2);

        // configurar adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        // configurar recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaDeTarefas.setLayoutManager(layoutManager);
        listaDeTarefas.setHasFixedSize(true);
        listaDeTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listaDeTarefas.setAdapter(tarefaAdapter);

    }


}