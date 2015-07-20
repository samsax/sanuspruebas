package com.sanus.appinicial;

import android.app.Activity;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Editar extends AppCompatActivity implements OnClickListener {
    private EditText nom, altura, peso, contra;
    private DatePicker fecha;
    private Spinner nejer, gen;
    private Button bEditar;
    String id;

    private static String username, date, pass, alt, pes, eje, sex,contrasena = "";

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //si lo trabajan de manera local en xxx.xxx.x.x va su ip local
    // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/cas/register.php";

    //testing on Emulator:
    private static final String BUSCA_URL = "http://databasebauq.zz.mu/start/Buscar_Usuario.php";
    private static final String EDITAR_URL = "http://databasebauq.zz.mu/start/Actualizar.php";
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        nom = (EditText) findViewById(R.id.nombre);
        contra = (EditText) findViewById(R.id.contrasena);
        fecha = (DatePicker) findViewById(R.id.fecha);
        altura = (EditText) findViewById(R.id.altura);
        peso = (EditText) findViewById(R.id.peso);
        nejer = (Spinner) findViewById((R.id.ejercicio));
        gen = (Spinner) findViewById(R.id.genero);
        bEditar = (Button) findViewById(R.id.actualizar);
        bEditar.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        Button bPerfil= (Button) findViewById(R.id.perfilt);
        bPerfil.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Editar.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
        Button bMen= (Button) findViewById(R.id.menut);
        bMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Editar.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        Button bLogout= (Button) findViewById(R.id.logout);
        bLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBHelper dataBase = new DBHelper(Editar.this, "DBUsuarios",null,1);
                SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
                dbwrite.delete("Usuario", "codigo=1", null);
                dbwrite.close();
                Intent intent = new Intent(Editar.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        new CargarDatos().execute();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        new EditUser().execute();
    }

    class EditUser extends AsyncTask<String, String, String> {
        int igual = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Editar.this);
            pDialog.setMessage("Actualizando el Usuario...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            DBHelper dataBase = new DBHelper(Editar.this, "DBUsuarios", null, 1);
            SQLiteDatabase dbwrite = dataBase.getWritableDatabase();
            int success;
            username = nom.getText().toString();
            String ano = Integer.toString(fecha.getYear());
            String mes = Integer.toString(fecha.getMonth());
            String dia = Integer.toString(fecha.getDayOfMonth());
            date = ano + "-" + mes + "-" + dia;
            pass = contra.getText().toString();
            alt = altura.getText().toString();
            pes = peso.getText().toString();
            eje = Integer.toString(nejer.getSelectedItemPosition());
            sex = Integer.toString(gen.getSelectedItemPosition());

            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("id", id));
                params.add(new BasicNameValuePair("nombre", username));
                params.add(new BasicNameValuePair("altura", alt));
                params.add(new BasicNameValuePair("fNacimiento", date));
                params.add(new BasicNameValuePair("peso", pes));
                params.add(new BasicNameValuePair("ejercicio", eje));
                params.add(new BasicNameValuePair("genero", sex));
                params.add(new BasicNameValuePair("contrasena", pass));
                Log.d("param", "." + contrasena + ".");
                Log.d("parametros","."+pass+ ".");
                if (pass.equals(contrasena)) {
                    //Posting user data to script
                    JSONObject json = jsonParser.makeHttpRequest(EDITAR_URL, "POST", params);

                    // full json response
                    Log.d("Registering attempt", json.toString());

                    // json success element
                    success = json.getInt(TAG_SUCCESS);
                    if (success > 0) {
                        Log.d("Usuario Editado!", json.toString());
                        igual = 1;
                        Intent intent = new Intent(Editar.this, Perfil.class);
                        startActivity(intent);
                        finish();
                        return json.getString(TAG_MESSAGE);
                    } else {
                        Log.d("Fallo en el registro", json.getString(TAG_MESSAGE));
                        return json.getString(TAG_MESSAGE);
                    }
                }
                else {
                    Log.d("Fallo en el registro","error contra");
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(igual == 0){
                        pDialog.cancel();
                        pDialog = new ProgressDialog(Editar.this);
                        pDialog.setMessage("Error en la contraseña");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(true);
                        pDialog.show();
                    }
                }

            });
                }

    }


    class CargarDatos extends AsyncTask<String, String, String> {
        String fechaNacimiento;
        String alturaN;
        String generoN ;
        String ejecicioN;
        String nombre;
        String pesoN;
        protected String doInBackground(String... args) {
            DBHelper dataBase = new DBHelper(Editar.this, "DBUsuarios", null, 1);
            SQLiteDatabase dbread = dataBase.getReadableDatabase();

            if (dbread != null) {
                //Consultamos si hay usuario
                Cursor c = dbread.rawQuery(" SELECT id FROM Usuario WHERE codigo=1 ", null);
                if (c.moveToFirst()) {
                    Log.d("id", c.getString(0));
                    id = c.getString(0);
                    dbread.close();
                    List params = new ArrayList();
                    params.add(new BasicNameValuePair(TAG_ID, id));
                    JSONObject json = jsonParser.makeHttpRequest(BUSCA_URL, "POST", params);
                    try {

                         fechaNacimiento = json.getString("fechaNacimiento");
                         alturaN = json.getString("altura");
                         generoN = json.getString("genero");
                         ejecicioN = json.getString("ejercicio");
                        nombre = json.getString("nombre");
                        pesoN = json.getString("peso");
                        contrasena = json.getString("contrasena");
                        Log.d("contra",contrasena);
                    }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    nom.setText(nombre);
                    altura.setText(alturaN);
                    peso.setText(pesoN);
                    gen.setSelection(Integer.parseInt(generoN));
                    nejer.setSelection(Integer.parseInt(ejecicioN));
                    String fechas[] = fechaNacimiento.split("-");
                    fecha.updateDate(Integer.parseInt(fechas[0]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[2]));

                }

            });
        }
    }
}

