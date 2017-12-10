package com.mazurco066.notepad.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.dao.NoteDAO;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    //Definindo Componentes
    private EditText editTitle;
    private EditText editNote;
    private Toolbar toolbar;

    //Definindo Atributos
    private NoteDAO dao;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Instanciando Componentes
        editTitle = findViewById(R.id.editTitle);
        editNote = findViewById(R.id.editNote);
        toolbar = findViewById(R.id.mainToolbar);

        //Instanciando Atributos
        dao = new NoteDAO(getApplicationContext());

        //Recuperando dados passados por intent
        Bundle data = getIntent().getExtras();
        if (data != null) {

            note = new Note();
            note.setId(data.getInt(DatabaseCreator.FIELD_ID));
            note.setDate(data.getString(DatabaseCreator.FIELD_DATE));
            note.setTitle(data.getString(DatabaseCreator.FIELD_TITLE));
            note.setContent(data.getString(DatabaseCreator.FIELD_CONTENT));

        }

        //configurando qual título ira aparecer na Activity
        String title = "New Note";
        if (note.getId() != -1) title = "Edit Note";

        //Configurando a Toolbar
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        this.setSupportActionBar(toolbar);

        //Definindo Valores passados  como parametro nos edittexts
        editTitle.setText(note.getTitle());
        editNote.setText(note.getContent());

    }

    //Sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu na activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return  true;
    }

    //Sobrescrevendo método para ver qual botão do menu foi pressionado

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual botão foi pressionado
        switch (item.getItemId()) {

            //Caso pressionou botão salvar
            case R.id.action_save:
                saveNote();
                break;

            //Caso pressionou botão deletar
            case R.id.action_delete:
                deleteNote();
                break;

            //Caso pressionou botão voltar
            case R.id.action_back:
                finish();
                break;

        }

        //Retorno padrão do método
        return super.onOptionsItemSelected(item);
    }

    //Método para executar ações do botão salvar nota
    private void saveNote() {

        //Verificando se os Campos não estão Vazios
        if (isValidFields()) {

            String title = editTitle.getText().toString();
            String content = editNote.getText().toString();

            note.setTitle(title);
            note.setContent(content);

            //Verifiando se é uma nova nota ou uma existente
            if (note.getId() == -1) {

                //Criando nova nota
                if (dao.insertNote(note)) {

                    //Retornando mensagem de sucesso ao criar nota para usuário
                    Toast.makeText(getApplicationContext(), "Note successfully created!", Toast.LENGTH_SHORT).show();

                    //Finalizando a Activity
                    finish();

                }
                else {

                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(getApplicationContext(), "Ops... an error occurred! Try again!", Toast.LENGTH_SHORT).show();
                }

            }
            else {

                //Atualizando a existente

            }

        }
        else {

            //Retornando alerta ao usuário
            Toast.makeText(getApplicationContext(), "Empty Fields!", Toast.LENGTH_SHORT).show();
        }

    }

    //Método para executar ações do botão deletar nota
    private void deleteNote() {

        //Verificando se o usuário esta tentando deletar uma nota que não existe
        if (note.getId() != -1) {

        }
        else {

            //Retornando alerta ao usuário
            Toast.makeText(getApplicationContext(), "You cannot delete a note that doesnt exists!!", Toast.LENGTH_SHORT).show();
        }

    }

    //Método para validar se os campos não estão vazios
    private boolean isValidFields() {

        return !(editTitle.getText().toString().isEmpty() || editNote.getText().toString().isEmpty());
    }

}
