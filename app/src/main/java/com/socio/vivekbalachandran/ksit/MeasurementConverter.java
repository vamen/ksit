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

import java.text.DecimalFormat;

/**
 * Created by Vivek Balachandran on 8/28/2015.
 */
public class MeasurementConverter extends android.support.v4.app.Fragment {

    //Text displayed on top of the fragment
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
        //inflating the layout
        root = inflater.inflate(R.layout.fragment_frame2, container, false);
        return root;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        //initializing all components of view after activity is created and view is free to accessed by fragment
        super.onActivityCreated(savedInstanceState);

        //EditText view where is data is recived from user
        editText = (EditText) root.findViewById(R.id.coninputText);
        editText.setHint("1");

        //textview where result after conversion is set
        textView = (TextView) root.findViewById(R.id.conoutputtext);

       //go button
        appCompatButton = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.conbutton);

        //text View to show type of conversion like length,temperature etc
        text1 = (TextView) root.findViewById(R.id.textView1);
        text1.setText(conlist[conposition]);

        //spinners for selecting the units for conversion
        android.support.v7.widget.AppCompatSpinner spinner1 = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.conspinner);

        //common array adapter for bot spinners
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, StaticDataproviderforMesurment.data[conposition]);
        spinner1.setAdapter(adapter);

        //spinner 2
        android.support.v7.widget.AppCompatSpinner spinner2 = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.conspinner2);
        spinner2.setAdapter(adapter);
       //click listeners
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "pos" + position + StaticDataproviderforMesurment.data[conposition][position], Toast.LENGTH_SHORT).show();

                //store the selected item position 'from unit'
                pos1 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "pos" + position + StaticDataproviderforMesurment.data[conposition][position], Toast.LENGTH_SHORT).show();

                //store the selected item position 'to unit'
                pos2 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the entered string is not null
                DecimalFormat df= new DecimalFormat("0.000000");
                if (!editText.getText().toString().matches("")) {

                    //get the entered value in double
                    double enetredvalue = Double.valueOf(editText.getText().toString());

                    //Toast.makeText(getActivity(), "clickedgo with value " + enetredvalue, Toast.LENGTH_SHORT).show();

                    //getconvertval function of StaticDataproviderforMesurement class that gets the converted value
                    double convertedvalue = StaticDataproviderforMesurment.getconvertval(enetredvalue, conposition, pos1, pos2);

                    textView.setText(df.format(convertedvalue));

                }
                //if the entered string is null take 1 the default value
                else if (editText.getText().toString().matches("")) {

                    double convertedvalue = StaticDataproviderforMesurment.getconvertval(1, conposition, pos1, pos2);

                    textView.setText(df.format(convertedvalue));

                    //Toast.makeText(getActivity(), "clickedgo with null string", Toast.LENGTH_SHORT).show();
                }


               // Toast.makeText(getActivity(), " " + StaticDataproviderforMesurment.data[conposition][pos1] +
                 //       " to " + StaticDataproviderforMesurment.data[conposition][pos2], Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setvalueofpositon(int position) {
        conposition = position;
    }
}
