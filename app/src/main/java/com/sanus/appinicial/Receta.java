package com.sanus.appinicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class Receta extends Activity implements View.OnClickListener{

    Button volver;
    ListView recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.receta);

        volver=(Button)findViewById(R.id.volver);
        recetas = (ListView)findViewById(R.id.recetalist);
        recetas.setAdapter(recetas.getAdapter());
        volver.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.volver:
                Intent i = new Intent(this, Menu.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}

