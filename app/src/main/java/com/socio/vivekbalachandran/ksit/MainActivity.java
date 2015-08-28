package com.socio.vivekbalachandran.ksit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.socio.vivekbalachandran.ksit.dataprovider.*;
//import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity{







    private Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbaroverlays);
        toolbar=(Toolbar)findViewById(R.id.act_bar);
        setSupportActionBar(toolbar);
      //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationFragment drawer;
        drawer =(NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.navigation);

       drawer.setup(R.id.navigation, (DrawerLayout) findViewById(R.id.nav_activity), toolbar);
        if(savedInstanceState==null)
            drawer.run();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.refresh) {
             getInstence(getBaseContext()).ncalls();
            Toast.makeText(this,"refreshed",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
