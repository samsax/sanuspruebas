package com.sanus.appinicial;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.view.View.OnClickListener;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BRIAN on 07/07/2015.
 */
public class Menu extends AppCompatActivity implements OnClickListener {

    public final static String EXTRA_MESSAGE = "com.sanus.appinicial.VerReceta";
    JSONParser jsonParser = new JSONParser();

    //testing on Emulator:
    private static final String MENU_URL = "http://databasebauq.zz.mu/start/Menu.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RECETA = "Receta";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_CALORIAS = "calorias";

    // products JSONArray
    JSONArray recetaArray = null;
    Button desayuno, media, almuerzo, algo, comida;
    ListView recetas;
    Button volver;
    ArrayAdapter<String> adaptador;
    ArrayList<HashMap<String, String>> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bLogout= (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(Menu.this, "DBUsuarios",null,1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        desayuno=(Button)findViewById(R.id.desayunoButton);
        media =(Button)findViewById(R.id.mediaButton);
        almuerzo =(Button)findViewById(R.id.almuerzoButton);
        algo =(Button)findViewById(R.id.algoButton);
        comida =(Button)findViewById(R.id.cenaButton);

        desayuno.setOnClickListener(this);
        media.setOnClickListener(this);
        almuerzo.setOnClickListener(this);
        algo.setOnClickListener(this);
        comida.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {

        String x="media";

        switch (v.getId()) {
            case R.id.desayunoButton:
                x="desayuno";
                break;
            case R.id.mediaButton:
                x="media";
                break;
            case R.id.almuerzoButton:
                x="almuerzo";
                break;
            case R.id.algoButton:
                x="algo";
                break;
            case R.id.cenaButton:
                x="comida";
                break;
            default:
                break;
        }

        Intent i = new Intent(this, CargarRecetas.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("comida", x);
        //i.putExtras(bundle);
        String message = x;
        i.putExtra(EXTRA_MESSAGE, message);
        startActivity(i);

    }



}

