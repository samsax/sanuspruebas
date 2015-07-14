package com.sanus.appinicial;

import android.content.Intent;
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

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BRIAN on 12/07/2015.
 */
public class CargarRecetas extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    private static final String MENU_URL = "http://databasebauq.zz.mu/start/Menu.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RECETA = "Receta";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_CALORIAS = "calorias";

    // products JSONArray
    JSONArray recetaArray = null;
    ListView recetas;
    ArrayList<HashMap<String, String>> lista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receta);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Menu.EXTRA_MESSAGE);
        Bundle bundle = getIntent().getExtras();
        Log.e("intent",message);
        LoadAllRecipe x =new LoadAllRecipe();
        x.setBoton(message);
        x.execute(message);

        //new LoadAllRecipe().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CargarRecetas.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CargarRecetas.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        //new LoadAllRecipe().execute();

    }

    class LoadAllRecipe extends AsyncTask<String, String, String> {

        private String b="";

        public void setBoton(String x){
            this.b=x;
        }

        protected String doInBackground(String... args) {
            // Building Parameters


            lista = new ArrayList<HashMap<String, String>>();

            List params = new ArrayList();
            params.add(new BasicNameValuePair(b, b));
            // getting JSON string from URL
            JSONObject js = jsonParser.makeHttpRequest(MENU_URL, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Recetas: ", js.toString());

            try {
                // Checking for SUCCESS TAG
                int success = js.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    recetaArray = js.getJSONArray(TAG_RECETA);
                    JSONObject r;
                    String name, cal;

                    for (int i = 0; i < recetaArray.length(); i++) {
                        r = recetaArray.getJSONObject(i);

                        // Storing each json item in variable
                        name = r.getString(TAG_NOMBRE);
                        cal = "Calorias: "+r.getString(TAG_CALORIAS);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_CALORIAS, cal);

                        lista.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {


            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    //setContentView(R.layout.receta);
                    recetas =(ListView)findViewById(R.id.recetalist);

                    ListAdapter adapter = new SimpleAdapter(
                            CargarRecetas.this,
                            lista,
                            android.R.layout.two_line_list_item,
                            new String[]{
                                    TAG_NOMBRE,
                                    TAG_CALORIAS,
                            },
                            new int[]{
                                    android.R.id.text1,
                                    android.R.id.text2,
                            });
                    // updating listview
                    recetas.setAdapter(adapter);
                }
            });
        }

    }


}
