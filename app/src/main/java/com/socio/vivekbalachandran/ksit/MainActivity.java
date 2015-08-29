package com.socio.vivekbalachandran.ksit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.socio.vivekbalachandran.ksit.dataprovider.*;
//import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements mediater{



    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction transaction;
    frameactivity fragment;
    NavigationFragment drawer;


    private Toolbar toolbar;
    Bundle savestate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbaroverlays);
        toolbar=(Toolbar)findViewById(R.id.act_bar);
         savestate=savedInstanceState;
        setSupportActionBar(toolbar);
      //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer =(NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.navigation);

        drawer.setup(R.id.navigation, (DrawerLayout) findViewById(R.id.nav_activity), toolbar);
         fragment=new frameactivity();
         fragmentManager=getSupportFragmentManager();
         transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.rlayout, fragment, "frameactivity");
        transaction.commit();

        Log.i("debug","oncreate");

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
             getInstence(getBaseContext(),savestate).ncalls();
            Toast.makeText(this,"refreshed",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void responce(int position) {

        if(position!=0) {
            frame2 fragment1 = new frame2();
            fragment1.setvalueofpositon(position-1);
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.rlayout, fragment1, "fragment");
            transaction.commit();
        }
        if(position==0)
        {
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.rlayout, fragment, "frameactivity");
            transaction.commit();

        }

            drawer.opensorclose(false);
    }
}
