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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConvertFragment extends android.support.v4.app.Fragment {

    //Array for spinners that displays currency units
    public static String[] curencyString1 =
            {
                    "INR:Indian Rupee",
                    "AUD: Australian Dollar",
                    "BGN:Bulgarian lev",
                    "BRL Brazilian real",
                    "CAD:Canadian Dollar",
                    "CHF:Swiss franc",
                    "CNY:Chinese Yuan",
                    "CZK:Czech Republic Koruna",
                    "DKK:Danish Krone",
                    "GBP:Great British Pound",
                    "HKD:Hong Kong Dollar",
                    "HRK:Croatian Kuna",
                    "HUF:Hungarian Forint",
                    "IDR:Indonesian Rupiah",
                    "ILS:Israeli New Sheqel",
                    "JPY:Japanese Yen ",
                    "KRW:South Korean Won",
                    "MXN:Mexican Peso ",
                    "MYR:Malaysian Ringgit",
                    "NOK: Norwegian Krone",
                    "NZD: New Zealand Dollar",
                    "PHP:Philippine Peso",
                    "PLN:Polish Zloty ",
                    "RON:Romanian Leu",
                    "RUB:Russian Ruble ",
                    "SEK:Swedish Krona",
                    "SGD:Singapore Dollar",
                    "THB:Thai Baht",
                    "TRY:Turkish Lira",
                    "USD:US Doller",
                    "ZAR:South African Rand",
                    "EUR: Euro"
            };

    //position of selected item in spinner 1
    public static int pos1 = 0;
    //position of selected item in spinner 1
    public static int pos2 = 0;
    protected String val;
    Context ctx;
    Dataprovider ob;
   //View object that is used to store or reference xml layout
    View root;
    TextView dateText;

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

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //obtain an instance from dataprovider single ton class
        ob = Dataprovider.getInstence(getActivity().getApplicationContext(), savedInstanceState);
        //edittext where currency input is read
        final EditText inputtext = (EditText) root.findViewById(R.id.inputText);
        inputtext.setHint("1");
        //Textview where result of the conversion is stored
        final TextView result = (TextView) root.findViewById(R.id.outputtext);

        //TextView the Displays last date when currency exchange rate is read.
        dateText=(TextView)root.findViewById(R.id.dateorerror);


        //spinners for selecting the units for converssion
        android.support.v7.widget.AppCompatButton button = (android.support.v7.widget.AppCompatButton) root.findViewById(R.id.view2);
        android.support.v7.widget.AppCompatSpinner spinner = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.spinner);
        //common Array adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, curencyString1);
        spinner.setAdapter(adapter);
        //spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "pos" + position + curencyString1[position], Toast.LENGTH_SHORT).show();
                pos1 = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner 2
        android.support.v7.widget.AppCompatSpinner spinner1 = (android.support.v7.widget.AppCompatSpinner) root.findViewById(R.id.spinner2);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "pos" + position + curencyString1[position], Toast.LENGTH_SHORT).show();
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
                    double convertedvalue = ob.getConvalue(pos1, pos2, Double.valueOf(inputtext.getText().toString()));
                    result.setText(String.valueOf(convertedvalue));
                    //Toast.makeText(getActivity(), "enter Done", Toast.LENGTH_SHORT).show();
                    //Log.d("oNeditif", "inside if listner");
                    return true;
                }
                return false;
            }
        });
        //button click listner to get the value entered by user
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the entered string is not null
                if (!inputtext.getText().toString().matches("")) {
                    //get the entered value in double
                    double enetredvalue = Double.valueOf(inputtext.getText().toString());

                    //Toast.makeText(getActivity(), "clickedgo with value"+enetredvalue, Toast.LENGTH_SHORT).show();

                    //getconvalue function of Dataprovider class that gets the converted value
                    if(!ob.get_date().matches("")) {
                        double convertedvalue = ob.getConvalue(pos1, pos2, enetredvalue);
                        result.setText(String.format( "%.5f", convertedvalue ));

                        dateText.setText("currency Exchange rate as on "+ob.get_date()+". \n * for latest Rates click refresh icon on top");
                    }
                    else {
                        dateText.setText("No data avilable, please click the refresh Icon on top and make shure that INTERNET is 'ON'");
                            }
                }//if the entered string is null take 1 the default value(when 'go' is pressed with out entering nothing
                else if (inputtext.getText().toString().matches("")) {

                   Toast.makeText(getActivity(), "Please enter some value bellow from", Toast.LENGTH_SHORT).show();
                }
                InputMethodManager mgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(inputtext.getWindowToken(), 0);
            }
        });
    }


}
