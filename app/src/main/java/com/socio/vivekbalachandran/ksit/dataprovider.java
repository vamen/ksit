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

/**
 * Created by Vivek Balachandran on 8/10/2015.
 */
public  class Dataprovider implements Runnable {


    public static double value[] = new double[32];

    URL url;
    public static Dataprovider instance=null;
    HttpURLConnection urlConnection;
    String datacaststring;
    final static String filename="valuefile.txt";
    Context ctx;

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

             if(getPreferencestatus()) {
                run();
                 Log.e("debug", "************datagetting***********");
             }
    }

    private boolean getPreferencestatus() {

        Log.e("debug", "************datagetting***********");
        SharedPreferences preferences=ctx.getSharedPreferences("START",Context.MODE_PRIVATE);
        boolean ischecked=preferences.getBoolean("STARTKEY", false);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("STARTKEY",true);
        editor.commit();
        return ischecked;
    }

    private Dataprovider()
    {

    }

   public  void ncalls(){new netcalls().execute();
    }

   public void writefile(double value[])
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
       } catch (IOException e) {
           e.printStackTrace();
       }

       finally {
           try {
               if(fileOutputStream!=null)
               fileOutputStream.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

   public void readvalue(double[] value)
   {
       FileInputStream fileInputStream=null;
       byte[] b=new byte[Dataprovider.value.length];
       Log.d("FILEI/O","reading from file");
       try {
           fileInputStream=ctx.openFileInput(filename);
           double read;
           DataInputStream dataInputStream=new DataInputStream(fileInputStream);
           for (int i=0;i< Dataprovider.value.length;i++) {
               read =dataInputStream.readDouble();
               if (read == -1) {
                   Log.d("FILE", filename + " is empty");
                   i= Dataprovider.value.length;

               } else {
                   Dataprovider.value[i] = read;

               }
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           try {
               fileInputStream.close();
               Log.d("FILEI/O","read from file");
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

   public double getConvalue(int position1,int position2,double invalue)
   {

       Log.d("EDITTEXT DEBUG",position1+"/"+invalue+"/"+position2+"/="+value[position2]);
             return     (invalue/value[position1])*value[position2];




   }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        readvalue(value);
        Log.d("ONBackground","executing");

    }

    public  class netcalls extends AsyncTask {

        public   netcalls()
      {
        //donothing;
      }
      @Override
      protected Object doInBackground(Object[] params) {
          try {
              url = new URL("http://api.fixer.io/latest?base=INR");
              urlConnection = (HttpURLConnection) url.openConnection();
              urlConnection.setRequestMethod("GET");
              urlConnection.connect();

              InputStream inputStream = urlConnection.getInputStream();
              BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
              StringBuffer stringBuffer = new StringBuffer();
              String line;
              while ((line = reader.readLine()) != null) {
                  stringBuffer.append(line + "\n");
              }
              if (inputStream == null)
                  datacaststring = null;

              Log.d("DEBUGDATA",stringBuffer.toString());
              jsonformater(stringBuffer.toString());


          } catch (MalformedURLException e) {

              Log.d("NO NETWORK ", "" + e);
              e.printStackTrace();
          } catch (ProtocolException e) {

              e.printStackTrace();
              Log.d("NO NETWORK ", "" + e);
          } catch (IOException e) {
              e.printStackTrace();
              Log.d("NO NETWORK ", "" + e);
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
    public void jsonformater(String data) throws JSONException {

        Log.d("DEBUGJSON","jsonformater function called");

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
        Log.d("VALUE OF USD","****"+value[29]+"****");
        Log.d("FILEI/O","Writing to file");
        writefile(value);
        Log.d("FILEI/O","Wrote to file");
        return;
    }
}
