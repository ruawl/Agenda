package com.example.android.agenda.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.agenda.R;
import com.example.android.agenda.fragments.DetalhesProvaFragment;
import com.example.android.agenda.fragments.ListaProvasFragment;
import com.example.android.agenda.modelos.Prova;

public class ListaProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if(isModoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean isModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();

        if (!isModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();

            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            detalhesFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal, detalhesFragment);
            tx.addToBackStack(null);

            tx.commit();
        } else {
            detalhesFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCampos(prova);
        }
    }
}
