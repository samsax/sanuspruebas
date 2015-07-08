package com.sanus.appinicial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BRIAN on 29/06/2015.
 */


public class Inicio extends Activity{

/*
    Bundle datos = this.getIntent().getExtras();
    int altura = datos.getInt("altura");
    float peso = datos.getFloat("peso");
    int edad = datos.getInt("edad");
    int genero = datos.getInt("gener");
    int ejercicio = datos.getInt("ejercicio");

    /*int altura = getIntent().getIntExtra("altura", 0);
    float peso = getIntent().getFloatExtra("peso", 0);
    int edad = getIntent().getIntExtra("edad", 0);
    int genero = getIntent().getIntExtra("gener", 0);
    int ejercicio = getIntent().getIntExtra("ejercicio",0);*/

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String INICIO_URL = "http://databasebauq.zz.mu/start/Inicio.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_IMC = "IMC";
    private static final String TAG_PesoI = "PesoIdeal";
    private static final String TAG_TMB = "TMB";
    private static int success;
    private static String I, P, T;



    double IMC, TMB , pesoIdeal;

    private TextView IndiceMC, TasaMB, pesoI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        IndiceMC = (TextView) findViewById(R.id.IMC);
        TasaMB = (TextView)findViewById(R.id.TMB);
        pesoI = (TextView)findViewById(R.id.pesoIdeal);
/*
        IndiceMC.setText(" ");
        TasaMB.setText(" ");
        pesoI.setText(" ");


        IndiceMC.setText(Integer.toString(success));
        TasaMB.setText(T);
        pesoI.setText("Hola");
*/
    }


    protected String doInBackground(String... args) {

        List params = new ArrayList();

        JSONObject json = jsonParser.makeHttpRequest(INICIO_URL, "GET", params );

        // full json response
        Log.d("Registering attempt", json.toString());



        try {



            // json success element
            success = json.getInt(TAG_SUCCESS);
            I = json.getString(TAG_IMC);
            P = json.getString(TAG_PesoI);
            T = json.getString(TAG_TMB);

            if (success == 1) {
                Log.d("Datos retornados", json.toString());
                //return json.getString(TAG_MESSAGE);

                IndiceMC.setText(I);
                TasaMB.setText(T);
                pesoI.setText(P);

            }else{
                Log.d("Fallo en la consulta", json.getString(TAG_SUCCESS));
                //return json.getString(TAG_MESSAGE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        IndiceMC.setText(Integer.toString(success));
        TasaMB.setText(T);
        pesoI.setText("hola");

        return null;

    }
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()== R.id.iramenu){
                Intent i = new Intent(this, Menu.class);
                startActivity(i);

        }
    }


/*
    public double calcularIMC(){
        IMC=peso/((altura/100)^2);
        return IMC;
    }

    public double calcularPeso(){
        pesoIdeal=21.7*((altura/100)^2);
        return pesoIdeal;
    }

    public double calcualrTMB(){
        TMB= (10*peso)+(6.25*altura)-(5*edad);
        if(genero==1){
            TMB = TMB+5;
        }else if(genero==2){
            TMB = TMB -161;
        }
        switch (ejercicio){
            case 1:
                TMB = TMB*1.2;
                break;
            case 2:
                TMB =TMB*1.375;
                break;
            case 3:
                TMB =TMB*1.55;
                break;
            case 4:
                TMB =TMB*1.725;
                break;
            case 5:
                TMB =TMB*1.9;
                break;
            default:
                break;
        }
        return TMB;
    }
*/
}
