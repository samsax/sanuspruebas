package com.sanus.appinicial;

import android.app.Activity;
<<<<<<< HEAD
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

=======
import android.os.Bundle;

/**
 * Created by Samuel on 08/07/2015.
 */
public class Menu extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
>>>>>>> 9dac5de3f2f747081262f323662d7c222d2ebeaf
    }
}
