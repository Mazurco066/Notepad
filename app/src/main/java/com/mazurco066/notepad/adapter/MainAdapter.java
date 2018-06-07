package com.mazurco066.notepad.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.ui.fragments.ListsFragment;
import com.mazurco066.notepad.ui.fragments.NotesFragment;

public class MainAdapter extends FragmentStatePagerAdapter {

    //Atributos
    private Context context;
    private String[] titles = new String[2];

    //Construtor padrão
    public MainAdapter(FragmentManager fm, Context context) {

        //Construindo com implementação padrão
        super(fm);

        //Reuperando Títulos das abas
        String tab1 = context.getResources().getString(R.string.tab_notes);
        String tab2 = context.getResources().getString(R.string.tab_lists);

        //Instanciando Atributos
        this.context = context;
        this.titles[0] = tab1;
        this.titles[1] = tab2;

    }

    //Sobrescrevendo método de recuperar faragment resposável pela aba
    @Override
    public Fragment getItem(int position) {

        //Instanciando fragment para retorno
        Fragment fragment = null;

        //Verificando qual aba está ativa
        switch (position) {

            //Case 1 - Notes
            case 0:
                fragment = new NotesFragment();
                break;

            //Case 2 - Lists
            case 1:
                fragment = new ListsFragment();
                break;
        }

        return fragment;
    }

    //Sobrescrevendo método respontável de retornar quantidade de abas
    @Override
    public int getCount() {

        //Retornando quantidade de abas
        return titles.length;

    }

    //Sobrescrevendo método de por título na aba por imagem
    @Override
    public CharSequence getPageTitle(int position) {

        //Retornando título da aba
        return titles[position];
    }

    //Sobrescrevendo método de onDestroy do ciclo de vida
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //Implementação padrão
        super.destroyItem(container, position, object);
    }
}
