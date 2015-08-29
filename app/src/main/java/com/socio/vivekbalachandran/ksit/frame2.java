package com.socio.vivekbalachandran.ksit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Vivek Balachandran on 8/28/2015.
 */
public class frame2 extends android.support.v4.app.Fragment {

    EditText editText;
    TextView textView;
    int conposition;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         root=inflater.inflate(R.layout.fragment_frame2,container,false);
        return root;
    }


        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            editText=(EditText)root.findViewById(R.id.coninputText);
            textView=(TextView)root.findViewById(R.id.conoutputtext);
            android.support.v7.widget.AppCompatSpinner spinner1=(android.support.v7.widget.AppCompatSpinner)root.findViewById(R.id.conspinner);


        }

        public void setvalueofpositon(int position)
        {
            conposition=position;
        }
}
