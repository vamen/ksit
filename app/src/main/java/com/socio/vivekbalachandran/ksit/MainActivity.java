package com.socio.vivekbalachandran.ksit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.socio.vivekbalachandran.ksit.Dataprovider.*;
//import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements Mediater {



    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction transaction;
    CurrencyConvertFragment fragment;
    NavigationFragment drawer;


    private Toolbar toolbar;
    Bundle savestate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbaroverlays);
       //custom action bar tryed to follow designed guidelines but i was not yet pro then
        toolbar=(Toolbar)findViewById(R.id.act_bar);

        setSupportActionBar(toolbar);
      //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get reference to navigation drawer
        drawer =(NavigationFragment)getSupportFragmentManager().findFragmentById(R.id.navigation);

        drawer.setup(R.id.navigation, (DrawerLayout) findViewById(R.id.nav_activity), toolbar);
                if(savedInstanceState==null)
            drawer.opensorclose(true);
        //set currency convert fragment dynamically

        fragment=new CurrencyConvertFragment();
         fragmentManager=getSupportFragmentManager();
         transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.rlayout, fragment, "frameactivity");
        transaction.commit();



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
            Intent intent=new Intent(this,SettingActivity.class);
            startActivity(intent);
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
      //currency conversion has a separate fragment so if position 0 is handled separately
        if(position!=0) {
            MeasurementConverter fragment1 = new MeasurementConverter();
            fragment1.setvalueofpositon(position-1);
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.rlayout, fragment1, "fragment");
            transaction.commit();
        }
        if(position==0)
        {//switch to currency conversion fragment
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.rlayout, fragment, "frameactivity");
            transaction.commit();

        }

            drawer.opensorclose(false);
    }
}
