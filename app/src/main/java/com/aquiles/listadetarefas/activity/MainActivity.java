package com.aquiles.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.aquiles.listadetarefas.DAO.TarefaDAO;
import com.aquiles.listadetarefas.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aquiles.listadetarefas.adapter.TarefaAdapter;
import com.aquiles.listadetarefas.databinding.ActivityMainBinding;
import com.aquiles.listadetarefas.helper.DbHelper;
import com.aquiles.listadetarefas.helper.RecyclerItemClickListener;
import com.aquiles.listadetarefas.model.Tarefa;

import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        // adicionar evento de clique
        listaDeTarefas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaDeTarefas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // recuperar tarefa para edicao
                                Tarefa tarefaSelecionada = listaTarefas.get(position);

                                // enviar tarefa para tela
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // recuperar tarefa
                                Tarefa tarefa = listaTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                // configurar titulo e mensagem
                                dialog.setTitle("Confirmar excluão");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefa.getNomeTarefa() + "?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                                        if (tarefaDAO.deletar(tarefa)) {
                                            carregarListaDeTarefas();

                                            Toast.makeText(getApplicationContext(), "Sucesso ao remover a tarefa", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Erro ao remover a tarefa", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

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
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();

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