package com.mazurco066.notepad.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.mazurco066.notepad.util.Preferences;


public class NoteActivity extends AppCompatActivity {

    //Definindo Componentes
    private EditText editTitle;
    private EditText editNote;
    private Toolbar toolbar;

    //Definindo Atributos
    private  Preferences preferences;
    private NoteDAO dao;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);

        //Verificando tema
        setSettingsTheme();

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
        String title = getResources().getString(R.string.activity_create_note);
        if (note.getId() != -1) title = getResources().getString(R.string.activity_update_note);

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

        //Recuperando mensagens a serem exibidas
        String emptyMsg = getResources().getString(R.string.alert_empty_fields);
        String sucessCreatedMsg = getResources().getString(R.string.alert_create_note_sucess);
        String sucessUpdatedMsg = getResources().getString(R.string.alert_save_note_sucess);
        String failure = getResources().getString(R.string.alert_failure);

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
                    Toast.makeText(getApplicationContext(), sucessCreatedMsg, Toast.LENGTH_SHORT).show();

                    //Finalizando a Activity
                    finish();

                }
                else {

                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(getApplicationContext(), failure, Toast.LENGTH_SHORT).show();

                }

            }
            else {

                //Atualizando a existente
                if (dao.editNote(note)) {

                    //Retornando mensagem de sucesso ao editar nota para usuário
                    Toast.makeText(getApplicationContext(), sucessUpdatedMsg, Toast.LENGTH_SHORT).show();

                }
                else {

                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(getApplicationContext(), failure, Toast.LENGTH_SHORT).show();

                }

            }

        }
        else {

            //Retornando alerta ao usuário
            Toast.makeText(getApplicationContext(), emptyMsg, Toast.LENGTH_SHORT).show();
        }

    }

    //Método para executar ações do botão deletar nota
    private void deleteNote() {

        //Verificando se o usuário esta tentando deletar uma nota que não existe
        if (note.getId() != -1) {

            //Confirmando exclusão da nota
            confirmDelete(note.getId());

        }
        else {

            //Recuperando mensagem
            String msg = getResources().getString(R.string.alert_inexistent_note);

            //Retornando alerta ao usuário
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

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

                    //Retomando a activity principal
                    finish();

                } else {

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

    //Método para validar se os campos não estão vazios
    private boolean isValidFields() {

        return !(editTitle.getText().toString().isEmpty() || editNote.getText().toString().isEmpty());
    }

    //Método para adaptar tema do app
    private void setSettingsTheme() {

        this.preferences = new Preferences(getApplicationContext());

        if (preferences.getTheme() != R.style.DarkTheme) {

            this.setTheme(preferences.getTheme());
        }
        setContentView(R.layout.activity_note);

    }

}
