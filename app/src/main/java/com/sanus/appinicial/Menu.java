package com.sanus.appinicial;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
public class Menu extends Activity implements OnClickListener {

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
    ArrayAdapter<String> adaptador;
    ArrayList<HashMap<String, String>> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

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

        LoadAllRecipe x = new LoadAllRecipe();

        switch (v.getId()) {
            case R.id.desayunoButton:
                x.setBoton("desayuno");
                break;
            case R.id.mediaButton:
                x.setBoton("media");
                break;
            case R.id.almuerzoButton:
                x.setBoton("almuerzo");
                break;
            case R.id.algoButton:
                x.setBoton("algo");
                break;
            case R.id.cenaButton:
                x.setBoton("comida");
                break;
            default:
                break;
        }

        x.execute();



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
            params.add(new BasicNameValuePair("boton", b));
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(MENU_URL, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("Recetas: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    recetaArray = json.getJSONArray(TAG_RECETA);
                    JSONObject r;
                    String name, cal;

                    for (int i = 0; i < recetaArray.length(); i++) {
                        r = recetaArray.getJSONObject(i);

                        // Storing each json item in variable
                        name = r.getString(TAG_NOMBRE);
                        cal = r.getString(TAG_CALORIAS);

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
                setContentView(R.layout.receta);
                recetas =(ListView)findViewById(R.id.recetalist);

                ListAdapter adapter = new SimpleAdapter(
                Menu.this,
                lista,
                R.layout.singlepost,
                new String[]{
                        TAG_NOMBRE,
                        TAG_CALORIAS,
                },
                new int[]{
                        R.id.single_post_nombre,
                        R.id.single_post_calorias,
                });
                // updating listview
                //setListAdapter(adapter);
                recetas.setAdapter(adapter);

            }
        });
        }

    }


}
