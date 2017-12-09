package com.mazurco066.notepad.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.dao.NoteDAO;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Definindo os Componentes
    private Toolbar toolbar;
    private ListView listView;

    //Definindo Atributos
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
        NoteDAO dao = new NoteDAO(getApplicationContext());

        //Definindo Toolbar a ser usada na activity
        this.setSupportActionBar(toolbar);

    }

    //Sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu no app
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    //sobrescrevento método de capturar ações feitas na toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual item de menu foi pressionado
        switch (item.getItemId()) {

            //Criar nova nota
            case R.id.action_write:
                writeNote();
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
        Intent noteActivity = new Intent(getApplicationContext(), NoteActivity.class);
        noteActivity.putExtra(DatabaseCreator.FIELD_ID, note.getId());
        noteActivity.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
        noteActivity.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
        noteActivity.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());

        //Iniciando nova activity
        startActivity(noteActivity);

    }

}
