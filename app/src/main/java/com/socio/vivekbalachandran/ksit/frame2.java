package com.socio.vivekbalachandran.ksit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vivek Balachandran on 8/28/2015.
 */
public class frame2 extends android.support.v4.app.Fragment {

    public String[] conlist = {

            " Length Conversions :",
            " Temperature Conversions :",
            " weight Conversions :",
            " Area Conversions :",
            " Time Conversions :",
            " Speed Conversions :",
            " volume Conversion :",


    };

    static int conposition = 0;
    static int pos1 = 0;
    static int pos2 = 0;
    EditText editText;
    TextView textView;
    TextView text1;
    android.support.v7.widget.AppCompatButton appCompatButton;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_frame2, container, false);
        return root;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = (EditText) root.findViewById(R.id.coninputText);
        textView = (TextView) root.findViewById(R.id.conoutputtext);
        appCompatButton = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.conbutton);
        text1=(TextView)root.findViewById(R.id.textView1);
        text1.setText(conlist[conposition]);

        android.support.v7.widget.AppCompatSpinner spinner1 = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.conspinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, staticdataprovider.data[conposition]);
        spinner1.setAdapter(adapter);
        android.support.v7.widget.AppCompatSpinner spinner2 = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.conspinner2);
        spinner2.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "pos" + position + staticdataprovider.data[conposition][position], Toast.LENGTH_SHORT).show();
                pos1 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "pos" + position + staticdataprovider.data[conposition][position], Toast.LENGTH_SHORT).show();
                pos2 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().matches("")) {
                    double enetredvalue=Double.valueOf(editText.getText().toString());

                    Toast.makeText(getActivity(), "clickedgo with value "+enetredvalue, Toast.LENGTH_SHORT).show();
                    double convertedvalue=staticdataprovider.getconvertval(enetredvalue,conposition,pos1, pos2 );
                    textView.setText(String.valueOf(convertedvalue));

                }
                else if(editText.getText().toString().matches("")){

                    double convertedvalue=staticdataprovider.getconvertval(1,conposition,pos1,pos2);
                    textView.setText(String.valueOf(convertedvalue));

                    Toast.makeText(getActivity(), "clickedgo with null string", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(getActivity(), " " + staticdataprovider.data[conposition][pos1] +
                        " to " + staticdataprovider.data[conposition][pos2], Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setvalueofpositon(int position) {
        conposition = position;
    }
}
