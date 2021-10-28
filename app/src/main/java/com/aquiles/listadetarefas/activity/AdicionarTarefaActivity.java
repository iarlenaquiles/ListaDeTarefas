package com.aquiles.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aquiles.listadetarefas.DAO.TarefaDAO;
import com.aquiles.listadetarefas.R;
import com.aquiles.listadetarefas.helper.DbHelper;
import com.aquiles.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                String nomeTarefa = editTarefa.getText().toString();
                if (!nomeTarefa.isEmpty()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);
                    tarefaDAO.salvar(tarefa);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}