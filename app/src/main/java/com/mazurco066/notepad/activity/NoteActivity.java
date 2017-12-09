package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mazurco066.notepad.R;

public class NoteActivity extends AppCompatActivity {

    //Definindo Componentes
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //Instanciando Componentes
        toolbar = findViewById(R.id.mainToolbar);

        //Configurando a Toolbar
        toolbar.setTitle("New Note");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        this.setSupportActionBar(toolbar);
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
                break;

            //Caso pressionou botão deletar
            case R.id.action_delete:
                break;

            //Caso pressionou botão voltar
            case R.id.action_back:
                finish();
                break;

        }

        //Retorno padrão do método
        return super.onOptionsItemSelected(item);
    }
}
