package com.sanus.appinicial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Samuel on 11/07/2015.
 */
public class VerReceta extends AppCompatActivity implements View.OnClickListener {
    JSONParser jsonParser = new JSONParser();

    private static final String ADD_URL="http://databasebauq.zz.mu/start/Sumar_Calorias.php";
    private static final String MENU_URL = "http://databasebauq.zz.mu/start/Ver_Recetas.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_URL = "url";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_CALORIAS = "calorias";
    private static final String TAG_CALDIA = "caloriasDia";
    WebView webview;
    TextView title;
    String rnombre;
    String rcalorias;
    String rurl;
    String id;
    int calorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.verreceta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Intent intent = getIntent();
        String message = intent.getStringExtra("resultado");
        Log.d("verreceta", message);
        LoadAllRecipe x =new LoadAllRecipe();
        x.setBoton(message);
        x.execute(message);
        webview = (WebView) findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.titulo);
        title.setText(message);
        webview.getSettings().setJavaScriptEnabled(true);
        setSupportActionBar(toolbar);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerReceta.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerReceta.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        Button bLogout= (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(VerReceta.this, "DBUsuarios",null,1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(VerReceta.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
         Button agregar=(Button)findViewById(R.id.agregar);
        agregar.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.agregar:
                new AgregarCalorias().execute();

                break;
            default:
                break;
        }
    }

    class LoadAllRecipe extends AsyncTask<String, String, String> {

        private String b="";


        public void setBoton(String x){
            this.b=x;
        }

        protected String doInBackground(String... args) {
            // Building Parameters


            List params = new ArrayList();
            params.add(new BasicNameValuePair("nombre", b));
            // getting JSON string from URL
            JSONObject js = jsonParser.makeHttpRequest(MENU_URL, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Recetas: ", js.toString());

            try {
                // Checking for SUCCESS TAG
                int success = js.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("VerRecetas: ", js.toString());
                     rnombre = js.getString(TAG_NOMBRE);
                     rcalorias = js.getString(TAG_CALORIAS);
                     rurl = js.getString(TAG_URL);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    webview.loadUrl(rurl);

                }

            });
        }
    }
    class AgregarCalorias extends AsyncTask<String, String, String> {
        DBHelper dataBase = new DBHelper(VerReceta.this, "DBUsuarios", null, 1);
        SQLiteDatabase dbread = dataBase.getReadableDatabase();
        SQLiteDatabase dbwrite = dataBase.getWritableDatabase();

        private String b="";

        protected String doInBackground(String... args) {
            // Building Parameters

            Cursor c = dbread.rawQuery("SELECT id FROM Usuario WHERE codigo=1 ", null);
            if (c.moveToFirst()) {
                Log.d("id", c.getString(0));
                id = c.getString(0);
                List params = new ArrayList();
                params.add(new BasicNameValuePair("calorias", rcalorias));
                params.add(new BasicNameValuePair("id", id));
                // getting JSON string from URL
                JSONObject js = jsonParser.makeHttpRequest(ADD_URL, "POST", params);

                // Check your log cat for JSON reponse
                Log.d("Recetas: ", js.toString());
                try {
                    // Checking for SUCCESS TAG
                    int success = js.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        calorias = js.getInt(TAG_CALDIA);
                        dbwrite.execSQL ("UPDATE Usuario " +
                                "SET calorias =" + calorias +
                                " WHERE id =" + id+";");
                        dbwrite.close();
                        Intent i = new Intent(VerReceta.this, Menu.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


    }
}

