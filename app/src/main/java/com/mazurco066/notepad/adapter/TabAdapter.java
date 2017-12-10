package com.mazurco066.notepad.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.fragments.LanguageFragment;
import com.mazurco066.notepad.fragments.ThemeFragment;

import java.util.HashMap;

public class TabAdapter extends FragmentStatePagerAdapter {

    //Atributos
    private Context context;
    private int[] icons = new int[]{
            R.drawable.ic_language,
            R.drawable.ic_compare
    };
    private int iconLength;

    //Construtor padrão
    public TabAdapter(FragmentManager fm, Context context) {

        //Construindo com implementação padrão
        super(fm);

        //Instanciando Atributos
        this.context = context;

        //Recuperando tamanho que ícone deverá ter com base no celular
        double scale = context.getResources().getDisplayMetrics().density;
        iconLength = (int) (36 * scale);

    }

    //Sobrescrevendo método de recuperar faragment resposável pela aba
    @Override
    public Fragment getItem(int position) {

        //Instanciando fragment para retorno
        Fragment fragment = null;

        //Verificando qual aba está ativa
        switch (position) {

            //Case 1 - Language
            case 1:
                fragment = new LanguageFragment();
                break;

            //Case 2 - Theme
            case 2:
                fragment = new ThemeFragment();
                break;
        }

        return fragment;
    }

    //Sobrescrevendo método respontável de retornar quantidade de abas
    @Override
    public int getCount() {

        //Retornando quantidade de abas
        return icons.length;

    }

    //Sobrescrevendo método de por título na aba por imagem
    @Override
    public CharSequence getPageTitle(int position) {

        //Recuperando Ícone da Aba Ativa
        Drawable drawable = ContextCompat.getDrawable(context, icons[position]);
        drawable.setBounds(0,0, iconLength, iconLength);

        //Colocando ícone dentro de uma String para retorno
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Retornando imagem como string
        return spannableString;
    }

    //Sobrescrevendo método de onDestroy do ciclo de vida
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //Implementação padrão
        super.destroyItem(container, position, object);
    }


}
