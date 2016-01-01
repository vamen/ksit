package com.socio.vivekbalachandran.ksit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.socio.vivekbalachandran.ksit.Dataprovider.getInstence;

/**
 * Created by Vivek Balachandran on 1/2/2016.
 */
public class Myreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager check = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo state=check.getActiveNetworkInfo();
        Log.d("Network","checking status");
        boolean isConnected=state.isConnectedOrConnecting();
        if(isConnected)
        {
            getInstence(context).ncalls();
            Log.d("Network","connectd");
        }
    }
}
