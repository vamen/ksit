package com.socio.vivekbalachandran.ksit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.os.Process;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vivek Balachandran on 8/10/2015.
 * singleton class that handles network calls for getting current day foreign exchange rates from fixar.io
 */
public  class Dataprovider implements Runnable {

    //Array that store the current day Foreign exchange rates
    public static double value[] = new double[32];

    URL url;
    //single instance
    public static Dataprovider instance=null;
    HttpURLConnection urlConnection;
    String datacaststring;
    //file name where data obtained from Internet is stored.
    final static String filename="valuefile.txt";
    Context ctx;
    public String LastWriteday;
    public static Dataprovider getInstence(Context ctx,Bundle savedInstancestate)
    {

        if(instance==null)
        {
            instance=new Dataprovider(ctx,savedInstancestate);
            return instance;
        }

        return instance;
    }

    private Dataprovider(Context ctx, Bundle savedInstancestate)
    {
        this.ctx=ctx;
        //if the application is opened first time then dont read data from the file.
             if(getPreferencestatus()) {
                run();
               //  Log.e("debug", "************datagetting***********");
             }
    }
    //functions that returns true when  file valuefile exists and data is stored in it
    private boolean getPreferencestatus() {

        //Log.e("debug", "************datagetting***********");
        SharedPreferences preferences=ctx.getSharedPreferences("START",Context.MODE_PRIVATE);
        boolean ischecked=preferences.getBoolean("STARTKEY", false);

        return ischecked;
    }
    //function that stores true into shared preference when data is stored into value file
    private void setpreferencestatus()
    { SharedPreferences preferences=ctx.getSharedPreferences("START",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor=preferences.edit();
      editor.putBoolean("STARTKEY",true);
        editor.commit();
    }

    private Dataprovider()
    {

    }

   public  void ncalls(){new netcalls().execute();
    }
//writing data obtained from Internet to file.
   public boolean writefile(double value[])
   {

       FileOutputStream fileOutputStream=null;
       DataOutputStream dataOutputStream;
       try {
           fileOutputStream=ctx.openFileOutput(filename, Context.MODE_PRIVATE);
           dataOutputStream=new DataOutputStream(fileOutputStream);
          for(int i=0;i<value.length;i++) {
              dataOutputStream.writeDouble(value[i]);

          }


       } catch (FileNotFoundException e) {
           e.printStackTrace();
           Log.d("FILE", "file not found");
           return false;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }

       finally {
           try {
               if(fileOutputStream!=null)
               fileOutputStream.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return true;
   }
//reading data stored in valuefile.txt
   public void readvalue(double[] value)
   {

           FileInputStream fileInputStream = null;
           byte[] b = new byte[Dataprovider.value.length];
           Log.d("FILEI/O", "reading from file");
           try {
               fileInputStream = ctx.openFileInput(filename);
               double read;
               DataInputStream dataInputStream = new DataInputStream(fileInputStream);
               for (int i = 0; i < Dataprovider.value.length; i++) {
                   read = dataInputStream.readDouble();
                   if (read == -1) {
                       Log.d("FILE", filename + " is empty");
                       i = Dataprovider.value.length;

                   } else {
                       Dataprovider.value[i] = read;

                   }
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               try {
                   fileInputStream.close();
                   Log.d("FILEI/O", "read from file");
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

   }
   //simple function that returns the currency converted value
   public double getConvalue(int position1,int position2,double invalue)
   {

      // Log.d("EDITTEXT DEBUG",position1+"/"+invalue+"/"+position2+"/="+value[position2]);
                       //INR vallue is the reference value taken 1 relative to it value b/w any two currency unit is obtained

             return     (invalue/value[position1])*value[position2];//(Design Technique:'Time and space trade off')




   }

    @Override
    //function is called every time the  MainActivity is opened
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        readvalue(value);

    }
//class that handles the network calls using AsyncTask, since Internet is used for very short amount of time.
    public  class netcalls extends AsyncTask {

        public   netcalls()
      {
        //donothing;
      }
      @Override
      protected Object doInBackground(Object[] params) {
          try {
              //Current day Foreign Exchange rate obtained from fixer.io
              url = new URL("http://api.fixer.io/latest?base=INR");
              urlConnection = (HttpURLConnection) url.openConnection();
              urlConnection.setRequestMethod("GET");
              urlConnection.connect();

              InputStream inputStream = urlConnection.getInputStream();
              Log.d("neteeork acess done","done");
              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
              StringBuffer stringBuffer = new StringBuffer();
              String line;
              while ((line = reader.readLine()) != null) {
                  stringBuffer.append(line + "\n");
              }
              if (inputStream == null)
                  datacaststring = null;

             // Log.d("DEBUGDATA",stringBuffer.toString());
             //Data obtained from the NET will be in JSON format which is read as String
             //following function is used to extract the specific data from the JSON string
              jsonformater(stringBuffer.toString());


          }catch (UnknownHostException e)
          {
           Log.d("NO network ","intrnet is not on");
          }
          catch (MalformedURLException e) {

              Log.d("NO NETWORK CALL", "" + e);
              e.printStackTrace();
          } catch (ProtocolException e) {

              e.printStackTrace();
              Log.d("NO NETWORK  access", "" + e);
          } catch (IOException e) {
              e.printStackTrace();
              Log.d("NO NETWORK streem error", "" + e);
          } catch (JSONException e) {
              e.printStackTrace();
          } finally {
              if (urlConnection != null) {
                  urlConnection.disconnect();

              }

          }

          return null;
      }
  }
    //This function is used to extract the specific data from the JSON string and writing to valuefile.txt
    public void jsonformater(String data) throws JSONException {

       // Log.d("DEBUGJSON","jsonformater function called");

        JSONObject JSONdatacastobject = new JSONObject(data);
        JSONObject jsonsubobject = JSONdatacastobject.getJSONObject("rates");
        value[0] = 1;
        value[1] = jsonsubobject.getDouble("AUD");
        value[2] = jsonsubobject.getDouble("BGN");
        value[3] = jsonsubobject.getDouble("BRL");
        value[4] = jsonsubobject.getDouble("CAD");
        value[5] = jsonsubobject.getDouble("CHF");
        value[6] = jsonsubobject.getDouble("CNY");
        value[7] = jsonsubobject.getDouble("CZK");
        value[8] = jsonsubobject.getDouble("DKK");
        value[9] = jsonsubobject.getDouble("GBP");
        value[10] = jsonsubobject.getDouble("HKD");
        value[11] = jsonsubobject.getDouble("HRK");
        value[12] = jsonsubobject.getDouble("HUF");
        value[13] = jsonsubobject.getDouble("IDR");
        value[14] = jsonsubobject.getDouble("ILS");
        value[15] = jsonsubobject.getDouble("JPY");
        value[16] = jsonsubobject.getDouble("KRW");
        value[17] = jsonsubobject.getDouble("MXN");
        value[18] = jsonsubobject.getDouble("MYR");
        value[19] = jsonsubobject.getDouble("NOK");
        value[20] = jsonsubobject.getDouble("NZD");
        value[21] = jsonsubobject.getDouble("PHP");
        value[22] = jsonsubobject.getDouble("PLN");
        value[23] = jsonsubobject.getDouble("RON");
        value[24] = jsonsubobject.getDouble("RUB");
        value[25] = jsonsubobject.getDouble("SEK");
        value[26] = jsonsubobject.getDouble("SGD");
        value[27] = jsonsubobject.getDouble("THB");
        value[28] = jsonsubobject.getDouble("TRY");
        value[29] = jsonsubobject.getDouble("USD");
        value[30] = jsonsubobject.getDouble("ZAR");
        value[31] = jsonsubobject.getDouble("EUR");
        //Log.d("VALUE OF USD","****"+value[29]+"****");
       // Log.d("FILEI/O","Writing to file");
        if(writefile(value))
        {
            store_date();
            setpreferencestatus();
        }
       // Log.d("FILEI/O","Wrote to file");
        return;
    }

    private void store_date() {

        DateFormat dateFormat=new SimpleDateFormat("dd-MMMM-yyyy");
        Date date=new Date();
        LastWriteday=dateFormat.format(date);
        SharedPreferences preferences=ctx.getSharedPreferences("START",ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("DATE",LastWriteday);
        editor.apply();
    }

    public  String get_date()
    {
        SharedPreferences preferences=ctx.getSharedPreferences("START",ctx.MODE_PRIVATE);
        String date=preferences.getString("DATE", "");
        return  date;
    }
}
