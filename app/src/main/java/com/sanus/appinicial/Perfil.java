package com.sanus.appinicial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.View.OnClickListener;
import android.support.v7.widget.Toolbar;


public class Perfil extends AppCompatActivity {

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
        setContentView(R.layout.perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        Button bAyu= (Button) findViewById(R.id.ayuda);
        bAyu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Ayuda.class);
                startActivity(intent);
                finish();
            }
        });
        Button bLogout= (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(Perfil.this, "DBUsuarios",null,1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(Perfil.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        // Hashmap para el ListView
        List = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadInfo().execute();
        IMC = (TextView) findViewById(R.id.textIMC);
        pesoI = (TextView) findViewById(R.id.textPesoIdeal);
        TMB = (TextView) findViewById(R.id.textTMB);
        name = (TextView) findViewById(R.id.hola);

        bMenu = (Button) findViewById(R.id.editar);
        bMenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Editar.class);
                startActivity(intent);
                finish();
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
            pDialog = new ProgressDialog(Perfil.this);
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
            DBHelper dataBase = new DBHelper(Perfil.this, "DBUsuarios",null,1);
            SQLiteDatabase dbread = dataBase.getReadableDatabase();
            if(dbread != null) {

                //Consultamos si hay usuario
                Cursor c = dbread.rawQuery("SELECT id FROM Usuario WHERE codigo=1 ", null);
                if (c.moveToFirst())
                {
                    Log.d("Enperfil", c.getString(0));
                    String b=c.getString(0);
                    Log.d("estoesb",b);
                    params.add(new BasicNameValuePair("id", b));

                    dbread.close();

                }

            }
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Perfil: ", json.toString());

            try{
                success = json.getInt(TAG_SUCCESS);
                I = json.getString(TAG_IMC);
                P = json.getString(TAG_PesoI);
                T = json.getString(TAG_TMB);
                N = json.getString(TAG_NOMBRE);

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

                    String textIMC = IMC.getText().toString();
                    String textpesoI = pesoI.getText().toString();
                    String textTMB = TMB.getText().toString();
                    String textHola = name.getText().toString();

                    name.setText(String.format("%s%s", textHola, N));
                    IMC.setText(String.format("%s%s",textIMC, I));
                    TMB.setText(String.format("%s%s",textTMB, T));
                    pesoI.setText(String.format("%s%s",textpesoI, P));

                }
            });
        }
    }
  }
