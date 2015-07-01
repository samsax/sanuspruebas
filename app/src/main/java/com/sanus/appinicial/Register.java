package com.sanus.appinicial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{
    private EditText nom, altura, peso, contra;
    private DatePicker fecha;
    private Spinner nejer, gen;
    private Button  mRegister;

    private static String username, date, pass, alt, pes, eje, sex ="";

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //si lo trabajan de manera local en xxx.xxx.x.x va su ip local
    // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/cas/register.php";

    //testing on Emulator:
    private static final String REGISTER_URL = "http://databasebauq.zz.mu/start/Registro.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nom = (EditText)findViewById(R.id.nombre);
        contra = (EditText)findViewById(R.id.contrasena);
        fecha = (DatePicker)findViewById(R.id.fecha);
        altura = (EditText)findViewById(R.id.altura);
        peso = (EditText)findViewById(R.id.peso);
        nejer = (Spinner)findViewById((R.id.ejercicio));
        gen = (Spinner)findViewById(R.id.genero);

        mRegister = (Button)findViewById(R.id.register);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        new CreateUser().execute();

        Intent intent = new Intent(Register.this,Datos.class);
        startActivity(intent);
        finish();
    }

    class CreateUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creando el Usuario...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            username = nom.getText().toString();
            String ano = Integer.toString(fecha.getYear());
            String mes = Integer.toString(fecha.getMonth());
            String dia = Integer.toString(fecha.getDayOfMonth());
            date = ano+"-"+mes+"-"+dia;
            pass = contra.getText().toString();
            alt = altura.getText().toString();
            pes = peso.getText().toString();
            eje = Integer.toString(nejer.getSelectedItemPosition());
            sex = Integer.toString(gen.getSelectedItemPosition());

            try {
                // Building Parameters
                List params = new ArrayList();
                params.add(new BasicNameValuePair("nombre", username));
                params.add(new BasicNameValuePair("altura", alt));
                params.add(new BasicNameValuePair("fecha", date));
                params.add(new BasicNameValuePair("peso",pes));
                params.add(new BasicNameValuePair("ejercicio", eje));
                params.add(new BasicNameValuePair("genero", sex));
                params.add(new BasicNameValuePair("contrasena", pass));


                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // full json response
                Log.d("Registering attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Usuario Creado!", json.toString());
                    return json.getString(TAG_MESSAGE);

                }else{
                    Log.d("Fallo en el registro", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

/*
        public void cambiarVentana(){
            Intent intent = new Intent(Register.this,Inicio.class);
            intent.putExtra("gener", gender());
            intent.putExtra("peso", weigth());
            intent.putExtra("altura", height());
            intent.putExtra("edad", age());
            intent.putExtra("ejercicio", exercise());


            startActivity(intent);
            finish();
        }
*/


    }

    public Integer age(){
        Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
             que se quiera recibir, por ejemplo ano mes dia "yyyy-MM-dd"
             en este caso es dia mes ano*/
            fechaNac = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaNacimiento.setTime(fechaNac);
        int ano = fechaActual.get(Calendar.YEAR)- fecha.getYear();
        int mes =fechaActual.get(Calendar.MONTH)- fecha.getMonth();
        int dia = fechaActual.get(Calendar.DATE)- fecha.getDayOfMonth();
        /*int ano = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);

        //Se ajusta el ano dependiendo el mes y el dia*/
        if(mes<0 || (mes==0 && dia<0)){
            ano--;
        }

        return ano;
    }

<<<<<<< HEAD
=======
    public float weigth(){
        float p = Float.parseFloat(pes);
        return p;
    }

    public int height(){
        float b = Float.parseFloat(alt);
        int a = (int)b*100;
        return a;
    }

    public int gender(){

        int g = Integer.parseInt(sex);
        return g;
    }

    public int exercise(){
        int e = Integer.parseInt(eje);
        return e;
    }


>>>>>>> 3a4297f1aaa3ce1fe1cff091903b017f54513100
}

