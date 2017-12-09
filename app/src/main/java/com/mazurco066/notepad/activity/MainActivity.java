package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.dao.NoteDAO;

public class MainActivity extends AppCompatActivity {

    //Definindo os Componentes
    private Toolbar toolbar;
    private ListView listView;

    //Definindo Atributos
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

    //Método que será responsávelpore xecutar as ações de recuperar dados de uma nota
    private void writeNote() {

    }

}
