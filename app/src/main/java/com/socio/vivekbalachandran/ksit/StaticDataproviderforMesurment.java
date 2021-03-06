package com.socio.vivekbalachandran.ksit;

import android.util.Log;

/**
 * Created by Vivek Balachandran on 8/29/2015.
 */
public  class StaticDataproviderforMesurment {

    static String[][] data={
            {
                    "milimeter",
                    "centimeter",
                    "meter",
                    "kilometer",
                    "miles",
                    "Foot"
            },
            {
                    "Celsius",
                    "kelvin",
                    "fahrenheit"

            },
            {
                    "Kilograms",
                    "Pounds",
                    "Stones",
                    "grains",
                    "Grams",
                    " Ounces"
            },
            {  "Hectares ",
                    "Acres ",
                    "Square Foot",
                    "Square Meters",
                    "Square Yards",



            },
            {
                    "Seconds",
                    "Minutes",
                    "Hour",
                    "Day"

            },
            {"Kilometer per hour",
                    "Miles per Hour",
                    "Meters per second",
                    "feets per secound",
                    "Kilometer per secound",
                    "Meter per minute",
                    "Centimeters per secound"
            },
            {
                    "Liter",
                    "Cubic Meter",
                    "US-Gellon"

            }
    };
    //an Array where one particular unit like meter,gram is taken as one as reference for all other conversions
     static double value[][]={
            { 1000, 100,1,0.001,0.000621,3.28084},
            {1,272.15,33.8},
            {0.001,0.00220,0.0001574,15.432,1,0.03527},
            {0.0001,0.000247,10.7639,1,1.1959},
            {3600,60,1,0.04166},
            {1,0.62137,0.2777,0.91134,0.0002777,16.66,27.77},
            {1,0.001,0.2641}

     };


        public static double getconvertval(double init,int conpos,int pos1,int pos2) {
            double conv = 0;
            if (conpos != 1) {
                conv = (init / value[conpos][pos1]) * value[conpos][pos2];
                return conv;
            }
            else {
               //explicit hardcoding for temperature conversion c:celsius f:fahrenheit k:kelvin
                if(pos1==0&&pos2==0)
                {
                    conv=init;//c to c,
                }
                if(pos1==0&&pos2==1){
                    conv=init+272.15;//c to k

                }
                if(pos1==0&&pos2==2){
                    conv=init*1.8+32;//c to f

                }
                if(pos1==1&&pos2==0){
                    conv=init-272.15; //k to c
                }
                if(pos1==1&&pos2==1){
                    conv=init;//k to k
                }
                if(pos1==1&&pos2==2){
                    conv=(init+273)*1.8+32; //k to f
                }
                if(pos1==2&&pos2==0){
                    conv=(init-32)/1.8;  //f to c
                }
                if(pos1==2&&pos2==1){
                    conv=((init-32)/1.8)+272.15; //f to k
                }
                if(pos1==2&&pos2==2){
                    conv=init;   //f to f
                }
                return conv;
            }
        }



}
