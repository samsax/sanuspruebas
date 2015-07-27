package com.sanus.appinicial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Ayuda extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> List;

    // url to get all products list
    private static String url = "http://databasebauq.zz.mu/start/Calcular_Datos.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_IMC = "IMC";
    private static final String TAG_PesoI = "PesoIdeal";
    private static final String TAG_TMB = "TMB";

    //ListView Lista;
    TextView IMC, pesoI, TMB, name;
    int success;
    String I, T, P, N;
    Button bMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bPerfil = (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ayuda.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen = (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ayuda.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        Button bLogout = (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(Ayuda.this, "DBUsuarios", null, 1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(Ayuda.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        IMC = (TextView) findViewById(R.id.textIMC);
        pesoI = (TextView) findViewById(R.id.textPesoIdeal);
        TMB = (TextView) findViewById(R.id.textTMB);

    }//fin onCreate
}




