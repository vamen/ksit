package com.socio.vivekbalachandran.ksit;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConvertFragment extends android.support.v4.app.Fragment {

//Array for spinners
    public static String[] curencyString1 =
            {"INR",
                    "AUD", "BGN", "BRL", "CAD", "CHF",
                    "CNY", "CZK", "DKK", "GBP", "HKD",
                    "HRK", "HUF", "IDR", "ILS", "JPY", "KRW",
                    "MXN", "MYR", "NOK", "NZK", "PHP", "PLN",
                    "RON", "RUB", "SEK", "SGD", "THB", "TRY",
                    "USD", "ZAR", "EUR"
            };
    protected String val;
    public  static int pos1=0;
    public static int pos2=0;
    Context ctx;
    Dataprovider ob;
    View root;




    public CurrencyConvertFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_frameactivity, container, false);

        return root;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ob = Dataprovider.getInstence(getActivity().getApplicationContext(), savedInstanceState);
        final EditText inputtext = (EditText) root.findViewById(R.id.inputText);
        inputtext.setHint("1");
        final TextView result=(TextView)root.findViewById(R.id.outputtext);
//        int val=Integer.parseInt((String)inputtext.getText().toString());
        android.support.v7.widget.AppCompatButton button = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.view2);
        android.support.v7.widget.AppCompatSpinner spinner = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, curencyString1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "pos" + position + curencyString1[position], Toast.LENGTH_SHORT).show();
                pos1 = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        android.support.v7.widget.AppCompatSpinner spinner1=(android.support.v7.widget.AppCompatSpinner)root.findViewById(R.id.spinner2);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "pos" + position + curencyString1[position], Toast.LENGTH_SHORT).show();
                pos2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        inputtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("oNedit", "inside listner");
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    double convertedvalue=ob.getConvalue(pos1, pos2, Double.valueOf(inputtext.getText().toString()));
                    result.setText(String.valueOf(convertedvalue));
                    Toast.makeText(getActivity(), "enter Done", Toast.LENGTH_SHORT).show();
                    Log.d("oNeditif", "inside if listner");
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputtext.getText().toString().matches("")) {
                    double enetredvalue=Double.valueOf(inputtext.getText().toString());

                    //Toast.makeText(getActivity(), "clickedgo with value"+enetredvalue, Toast.LENGTH_SHORT).show();
                    double convertedvalue=ob.getConvalue(pos1, pos2,enetredvalue );
                    result.setText(String.valueOf(convertedvalue));

                }
                else if(inputtext.getText().toString().matches("")){

                    double convertedvalue=ob.getConvalue(pos1, pos2, 1);
                    result.setText(String.valueOf(convertedvalue));

                    //Toast.makeText(getActivity(), "clickedgo with null string", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
