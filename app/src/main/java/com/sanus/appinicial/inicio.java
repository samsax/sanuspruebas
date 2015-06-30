package com.sanus.appinicial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by BRIAN on 29/06/2015.
 */


public class Inicio extends Activity{
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

    double IMC, TMB , pesoIdeal;

    private TextView IndiceMC, TasaMB, pesoI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        IndiceMC = (TextView) findViewById(R.id.IMC);
        TasaMB = (TextView)findViewById(R.id.TMB);
        pesoI = (TextView)findViewById(R.id.pesoIdeal);

        IndiceMC.setText(Double.toString(calcularIMC()));
        TasaMB.setText(Double.toString(calcualrTMB()));
        pesoI.setText(Double.toString(calcularPeso()));

    }

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

}
