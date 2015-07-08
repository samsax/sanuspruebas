package com.sanus.appinicial;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

/**
 * Created by BRIAN on 07/07/2015.
 */
public class Menu extends Activity implements View.OnClickListener{
    private Button desayuno, media, almuerzo, algo, comida;

    JSONParser jsonParser = new JSONParser();

    //testing on Emulator:
    private static final String REGISTER_URL = "http://databasebauq.zz.mu/start/Registro.php";

    //ids
    private static final String TAG_RECETA = "Receta";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_CALORIAS = "calorias";



    @Override
    public void onClick(View v) {

    }
}
