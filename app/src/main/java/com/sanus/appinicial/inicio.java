package com.sanus.appinicial;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class inicio extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Spinner ejecicio = (Spinner) findViewById(R.id.ejecicio);
        Spinner genero = (Spinner) findViewById(R.id.genero);
        String[] values = new String[]{getString(R.string.Poco), getString(R.string.Ligero), getString(R.string.Moderado),
                getString(R.string.Fuerte), getString(R.string.MuyFuerte)};
        String[] generos = new String[]{getString(R.string.Masculino),getString(R.string.Femenino)};
        ArrayAdapter<String> adapterEje = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        ArrayAdapter<String> adapterGen = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, generos);
        ejecicio.setAdapter(adapterEje);
        genero.setAdapter(adapterGen);
       /* final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isCancelled() {
        return true;
    }
}
