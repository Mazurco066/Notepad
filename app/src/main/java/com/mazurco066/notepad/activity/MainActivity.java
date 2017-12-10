package com.mazurco066.notepad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.NoteAdapter;
import com.mazurco066.notepad.dao.NoteDAO;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Definindo os Componentes
    private Toolbar toolbar;
    private ListView listView;

    //Definindo Atributos
    private ArrayAdapter<Note> adapter;
    private List<Note> notes;
    private NoteDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando os Componentes
        toolbar = findViewById(R.id.mainToolbar);
        listView = findViewById(R.id.noteListView);

        //Instanciando Atributos
        this.dao = new NoteDAO(getApplicationContext());

        //Definindo Toolbar a ser usada na activity
        this.setSupportActionBar(toolbar);

        //Ouvindo eventos de clicar em uma nota
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Instanciando nota
                Note note = notes.get(i);

                //Iniciando Activity de Edição de nota
                openNoteActivity(note);

            }
        });

        //Ouvindo eventos de segurar click em uma nota
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Confirmando exclusão da nota selecionada com usuário
                confirmDelete(notes.get(i).getId());

                //Retornando
                return true;
            }
        });

    }

    //Sobrescrevendo método de ao retomar a activity
    @Override
    protected void onResume() {

        //Implementação padrão do método de onResume do ciclo de vida
        super.onResume();

        //Verificando se há itens na lista
        this.notes = dao.readAllNotes();
        if (notes != null) {

            //Atualizando lista de notas
            this.adapter = new NoteAdapter(getApplicationContext(), notes);
            this.listView.setAdapter(adapter);

        }

    }

    //Sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu no app
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    //Sobrescrevento método de capturar ações feitas na toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual item de menu foi pressionado
        switch (item.getItemId()) {

            //Criar nova nota
            case R.id.action_write:
                writeNote();
                break;

            //Configurações do app
            case R.id.action_settings:
                openSettingsActivity();
                break;

            //Sair do App
            case R.id.action_exit:
                finish();
                break;

        }

        //Retornando retorno padrão do método implementado na superclasse
        return super.onOptionsItemSelected(item);

    }

    //Método que será responsável por executar as ações de recuperar dados de uma nota
    private void writeNote() {

        //Instanciando uma nova nota com id de não salva para validação
        Note note = new Note();
        note.setId(-1);

        //Abrindo Activity de edição para nova nota
        openNoteActivity(note);

    }

    //Método para Abrir Activity de Escrever/Visualizar Nota
    private void openNoteActivity(Note note) {

        //Instanciando intent para ir para prox activity
        Intent noteActivity = new Intent(MainActivity.this, NoteActivity.class);
        noteActivity.putExtra(DatabaseCreator.FIELD_ID, note.getId());
        noteActivity.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
        noteActivity.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
        noteActivity.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());

        //Iniciando nova activity
        startActivity(noteActivity);

    }

    //Método para abrir activity de configurações
    private void openSettingsActivity() {

        //Instanciando intent e indo para settings
        Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settings);
    }

    //Método para confirmar exclusão de uma nota
    private void confirmDelete(final int id) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Configurando alert dialog
        alertDialog.setTitle("Delete Confirmation");
        alertDialog.setMessage("Are you sure you want to delete this note?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Recuperando mensagens
                String sucess = getResources().getString(R.string.alert_delete_note_sucess);
                String failure = getResources().getString(R.string.alert_failure);

                if (dao.deleteNote(id)) {

                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(getApplicationContext(), sucess, Toast.LENGTH_SHORT).show();

                    //Atualizando Lista
                    onResume();

                }
                else {

                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(getApplicationContext(), failure, Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Nada Acontece....
            }
        });

        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();

    }

}
