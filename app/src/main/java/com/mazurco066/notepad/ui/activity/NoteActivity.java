package com.mazurco066.notepad.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.presenter.NotesPresenter;
import com.mazurco066.notepad.task.NotesTask;
import com.mazurco066.notepad.util.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity implements NotesTask.View {

    //Definindo Componentes
    @BindView(R.id.editTitle) EditText editTitle;
    @BindView(R.id.editNote) EditText editNote;
    @BindView(R.id.mainToolbar) Toolbar toolbar;

    //Definindo Atributos
    private Preferences preferences;
    private NotesPresenter presenter;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);

        //Verificando tema
        setSettingsTheme();

        //Interligando componentes com xml
        ButterKnife.bind(this);

        //Instanciando Componentes
        presenter = new NotesPresenter(this, getApplicationContext());

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
                //Recuperando mensagens a serem exibidas
                String emptyMsg = getResources().getString(R.string.alert_empty_fields);

                //Verificando se os Campos não estão Vazios
                if (isValidFields()) {
                    //Preenchendo dados da nota
                    note.setTitle(editTitle.getText().toString());
                    note.setContent(editNote.getText().toString());

                    //Tentando inserir nota
                    presenter.save(note);
                }
                else { Toast.makeText(getApplicationContext(), emptyMsg, Toast.LENGTH_SHORT).show(); }
                break;

            //Caso pressionou botão compartilhar
            case R.id.action_share:
                presenter.share(note, this);
                break;

            //Caso pressionou botão deletar
            case R.id.action_delete:
                confirmDelete(note.getId());
                break;

            //Caso pressionou botão voltar
            case R.id.action_back:
                finish();
                break;

        }

        //Retorno padrão do método
        return super.onOptionsItemSelected(item);
    }

    //Método para confirmar exclusão de uma nota
    private void confirmDelete(final int id) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Configurando alert dialog
        alertDialog.setTitle(getResources().getString(R.string.dialog_note_delete_title));
        alertDialog.setMessage(getResources().getString(R.string.dialog_note_delete_content));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { presenter.delete(id); }});

        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { /*Nada Acontece....*/}});

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

    @Override
    public void onSaveSuccess() {

        //Recuperando mensagem de retorno
        String sucessCreatedMsg = getResources().getString(R.string.alert_create_note_sucess);

        //Retornando mensagem de sucesso ao criar nota para usuário
        Toast.makeText(getApplicationContext(), sucessCreatedMsg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateSucess() {
        View view = findViewById(R.id.note_activity);
        Snackbar snackbar = Snackbar.make(view, getString(R.string.alert_save_note_sucess), Snackbar.LENGTH_SHORT);
        //Customizando a snackbar
        TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackActionView.setTextColor(Color.WHITE);
        snackActionView.setTextSize(16);
        snackbar.getView().setBackground(getDrawable(R.drawable.themed_snackbar));
        //Mostrando a snackbar
        snackbar.show();
    }

    @Override
    public void onSaveFailed() {
        View view = findViewById(R.id.note_activity);
        Snackbar snackbar = Snackbar.make(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
        //Customizando a snackbar
        TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackActionView.setTextColor(Color.WHITE);
        snackActionView.setTextSize(16);
        snackbar.getView().setBackground(getDrawable(R.drawable.themed_snackbar));
        //Mostrando a snackbar
        snackbar.show();
    }

    @Override
    public void onDeleteSuccess() {

        //Recuperando mensagem de sucesso
        String sucess = getResources().getString(R.string.alert_delete_note_sucess);

        //Retornando mensagem de sucesso ao editar nota para usuário
        Toast.makeText(getApplicationContext(), sucess, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDeleteFailed(int code) {

        //Recuperando mensagens de erro possiveis
        View view = findViewById(R.id.note_activity);
        Snackbar snackbar;
        String e101 = getResources().getString(R.string.alert_failure);
        String e102 = getResources().getString(R.string.alert_inexistent_note);

        switch (code) {
            case 101:
                snackbar = Snackbar.make(view, e101, Snackbar.LENGTH_SHORT);
                break;
            case 102:
                snackbar = Snackbar.make(view, e102, Snackbar.LENGTH_SHORT);
                break;
            default:
                snackbar = Snackbar.make(view, e102, Snackbar.LENGTH_LONG);
        }

        TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackActionView.setTextColor(Color.WHITE);
        snackActionView.setTextSize(16);
        snackbar.getView().setBackground(getDrawable(R.drawable.themed_snackbar));
        //Mostrando a snackbar
        snackbar.show();
    }

    @Override
    public void onShareFailed() {
        View view = findViewById(R.id.note_activity);
        Snackbar snackbar = Snackbar.make(view, getString(R.string.alert_empty_shared_field), Snackbar.LENGTH_SHORT);
        //Customizando a snackbar
        TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackActionView.setTextColor(Color.WHITE);
        snackActionView.setTextSize(16);
        snackbar.getView().setBackground(getDrawable(R.drawable.themed_snackbar));
        //Mostrando a snackbar
        snackbar.show();
    }
}
