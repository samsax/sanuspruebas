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


public class Inicio extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> List;

    // url to get all products list
    private static String url = "http://databasebauq.zz.mu/start/Fin_Dia.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PesoM = "pesoMes";


    //ListView Lista;
    TextView tdia, pesoM, tsug, tmensaje;
    int success;
    String I, T, P, N;
    Button bfindia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        Button bLogout= (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(Inicio.this, "DBUsuarios",null,1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(Inicio.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        // Hashmap para el ListView

        // Cargar los productos en el Background Thread

        tdia = (TextView) findViewById(R.id.dia);
        pesoM = (TextView) findViewById(R.id.peso);
        tsug = (TextView) findViewById(R.id.sugeridas);
        tmensaje = (TextView) findViewById(R.id.mensaje);
        pesoM.setVisibility(View.INVISIBLE);
        tmensaje.setVisibility(View.INVISIBLE);
        bfindia = (Button) findViewById(R.id.findia);
        bfindia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new LoadInfo().execute();
            }
        });

    }//fin onCreate



    class LoadInfo extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Inicio.this);
            pDialog.setMessage("Calculando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            DBHelper dataBase = new DBHelper(Inicio.this, "DBUsuarios",null,1);
            SQLiteDatabase dbread = dataBase.getReadableDatabase();
            if(dbread != null) {

                //Consultamos si hay usuario
                Cursor c = dbread.rawQuery("SELECT id FROM Usuario WHERE codigo=1 ", null);
                if (c.moveToFirst())
                {
                    Log.d("Enperfil", c.getString(0));
                    String b=c.getString(0);
                    params.add(new BasicNameValuePair("id", b));

                    dbread.close();

                }

            }
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Inicio: ", json.toString());

            try{
                success = json.getInt(TAG_SUCCESS);
                P = json.getString(TAG_PesoM);


            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    String textIMC = tdia.getText().toString();
                    String textpesoI = pesoM.getText().toString();
                    String textTMB = tsug.getText().toString();
                    String textHola = tmensaje.getText().toString();
                    pesoM.setVisibility(View.VISIBLE);
                    pesoM.setText(String.format("%s%s", textpesoI, P));

                }
            });
        }
    }
  }
