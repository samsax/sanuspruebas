package com.sanus.appinicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BRIAN on 10/07/2015.
 */
public class Receta extends AppCompatActivity {

    ListView recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.receta);
        CargarRecetas c = new CargarRecetas();
        recetas = (ListView)findViewById(R.id.recetalist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Receta.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Receta.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });


    }
    }

