package com.sanus.appinicial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Datos extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> List;


    // url to get all products list
    private static String url = "http://databasebauq.zz.mu/start/Inicio.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_IMC = "IMC";
    private static final String TAG_PesoI = "PesoIdeal";
    private static final String TAG_TMB = "TMB";
    // products JSONArray
    JSONArray products = null;

    //ListView Lista;
    TextView IMC, pesoI, TMB;
    int success;
    String I, T, P;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        // Hashmap para el ListView
        List = new ArrayList<HashMap<String, String>>();

        // Cargar los productos en el Background Thread
        new LoadAllProducts().execute();
        //Lista = (ListView) findViewById(R.id.listAll);
        IMC = (TextView) findViewById(R.id.textIMC);
        pesoI = (TextView) findViewById(R.id.textPesoIdeal);
        TMB = (TextView) findViewById(R.id.textTMB);


    }//fin onCreate


    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Datos.this);
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
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Datos: ", json.toString());

            try{
                success = json.getInt(TAG_SUCCESS);
                I = json.getString(TAG_IMC);
                P = json.getString(TAG_PesoI);
                T = json.getString(TAG_TMB);

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

                    IMC.setText(String.format("%s%s",textIMC, I));
                    TMB.setText(String.format("%s%s",textTMB, T));
                    pesoI.setText(String.format("%s%s",textpesoI, P));

                }
            });
        }
    }
}