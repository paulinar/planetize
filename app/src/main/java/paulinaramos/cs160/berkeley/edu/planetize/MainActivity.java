package paulinaramos.cs160.berkeley.edu.planetize;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends Activity {

    private TextView ifImText;
    private TextView onPlanetText;
    private TextView thenImText;
    private TextView onPlanetText2;
    private EditText myNum;
    private Button planetizeMe;
    private Button reset;
    private double value = 0;
    private TextView calculatedNum;
    private TextView unitReflector;

    Spinner unitPicker;
    Spinner firstPlanetPicker;
    Spinner secondPlanetPicker;

    private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 3000;

    private ImageView splashscreen;

    //handler for splash screen
//    private Handler splashHandler = new Handler() {
//        /* (non-Javadoc)
//        * @see android.os.Handler#handleMessage(android.os.Message)
//        */
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case STOPSPLASH:
//                    //remove SplashScreen from view
//                    splashscreen.setVisibility(View.GONE);
//
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        /*** TEXT ***/
        Typeface customFont = Typeface.createFromAsset(getAssets(),
                "codelight.otf");
        ifImText = (TextView) findViewById(R.id.ifImText);
        ifImText.setTypeface(customFont);
        onPlanetText = (TextView) findViewById(R.id.onPlanetText);
        onPlanetText.setTypeface(customFont);
        thenImText = (TextView) findViewById(R.id.thenImText);
        thenImText.setTypeface(customFont);
        onPlanetText2 = (TextView) findViewById(R.id.onPlanetText2);
        onPlanetText2.setTypeface(customFont);

        calculatedNum = (TextView) findViewById(R.id.calculatedNum);
        calculatedNum.setFocusable(false);

//        /***  SPLASH SCREEN ***/
//        splashscreen = (ImageView) findViewById(R.id.splashscreen);
//        Message msg = new Message();
//        msg.what = STOPSPLASH;
//        splashHandler.sendMessageDelayed(msg, SPLASHTIME);


        /*** [1] UNIT SPINNER ***/

        unitPicker = (Spinner) findViewById(R.id.unitPicker);

        String[] units = {"years old", "days old", "pounds"};

        MyArrayAdapter unitAdapter = new MyArrayAdapter
                (this, R.layout.spinner_item, units);

        unitAdapter.setDropDownViewResource(R.layout.spinner_item);

        unitPicker.setAdapter(unitAdapter);

        unitPicker.setBackgroundResource(R.drawable.border);

        unitPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitReflectorTrigger(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*** [2] FIRST PLANET SPINNER ***/

        firstPlanetPicker = (Spinner) findViewById(R.id.firstPlanetPicker);

        String[] planets = {
                "Mercury",
                "Venus",
                "Earth",
                "Mars",
                "Jupiter",
                "Saturn",
                "Uranus",
                "Neptune"

        };

        MyArrayAdapter firstPlanetAdapter = new MyArrayAdapter
                (this, R.layout.spinner_item, planets);

        firstPlanetAdapter.setDropDownViewResource(R.layout.spinner_item);

        firstPlanetPicker.setAdapter(firstPlanetAdapter);

        firstPlanetPicker.setBackgroundResource(R.drawable.border);

        firstPlanetPicker.setSelection(2);


        /*** [3] UNIT REFLECTOR TEXT ***/

        unitReflector = (TextView) findViewById(R.id.unitReflector);
        unitReflector.setTypeface(customFont);
        unitReflector.setFocusable(false);


        /*** [4] SECOND PLANET SPINNER ***/

        secondPlanetPicker = (Spinner) findViewById(R.id.secondPlanetPicker);

        MyArrayAdapter secondPlanetAdapter = new MyArrayAdapter
                (this, R.layout.spinner_item, planets);

        secondPlanetAdapter.setDropDownViewResource(R.layout.spinner_item);

        secondPlanetPicker.setAdapter(firstPlanetAdapter);

        secondPlanetPicker.setBackgroundResource(R.drawable.border);


        /* OTHER COMPONENTS */

        myNum = (EditText) findViewById(R.id.myNum);

        myNum.setTypeface(customFont);

        calculatedNum.setTypeface(customFont);

        planetizeMe = (Button) findViewById(R.id.planetizeMeButton);
        planetizeMe.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               convertValues();
                                           }
                                       }
        );

        planetizeMe.setHeight(100);

        planetizeMe.setTypeface(customFont);

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               reset();
                                           }
                                       }
        );

        reset.setTypeface(customFont);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* CUSTOM METHODS BEGIN HERE */

    void reset() {
        myNum.setText("");
        calculatedNum.setText("0");
        firstPlanetPicker.setSelection(2);
        secondPlanetPicker.setSelection(0);
        unitPicker.setSelection(0);
    }

    int chosenUnit = 0; // 0 is "years old", 1 is "pounds"

    void unitReflectorTrigger(int chosenUnit) {

        switch (chosenUnit) {
            case 0:
                unitReflector.setText("y e a r s  o l d");
                break;
            case 1:
                unitReflector.setText("d a y s  o l d");
                break;
            case 2:
                unitReflector.setText("p o u n d s");
                break;
        }
    }

    void convertValues() {
        int firstPlanet = firstPlanetPicker.getSelectedItemPosition();
        int secondPlanet = secondPlanetPicker.getSelectedItemPosition();
        double resultOnEarth = 0.0;
        double finalResult = 0.0;
        chosenUnit = unitPicker.getSelectedItemPosition();

        if (myNum.getText().length() > 0) {
            value = Double.parseDouble(myNum.getText().toString());

            /* always convert to Earth first, then convert to other planet */
            resultOnEarth = earthCalculator(value, chosenUnit, firstPlanet);
            finalResult = otherPlanetCalculator(resultOnEarth, chosenUnit, secondPlanet);
        }

        DecimalFormat dF = new DecimalFormat("#.##");
        calculatedNum.setText(dF.format(finalResult));
    }

    double earthCalculator(double value, int chosenUnit, int firstPlanet)
    {
        double retval = 0.0;
        switch(chosenUnit)
        {
            case 0:retval = calculateYearsAgeOnEarth(value, firstPlanet); break;
            case 1:retval = calculateDaysAgeOnEarth(value, firstPlanet); break;
            case 2:retval = calculateWeightOnEarth(value, firstPlanet); break;
        }
        return(retval);
    }

    double otherPlanetCalculator(double resultOnEarth, int chosenUnit, int secondPlanet)
    {
        double retval = 0.0;
        switch(chosenUnit)
        {
            case 0:retval = calculateYearsAgeOnOther(resultOnEarth, secondPlanet); break;
            case 1:retval = calculateDaysAgeOnOther(resultOnEarth, secondPlanet); break;
            case 2:retval = calculateWeightOnOther(resultOnEarth, secondPlanet); break;
        }
        return(retval);
    }

    /* AGE (YEARS) ON EARTH */
    double calculateYearsAgeOnEarth (double value, int planet)
    {
        double age = 0.0;
        switch(planet)
        {
            case 0:age= (value*87.96)/365;   break; // Mercury
            case 1:age= (value*224.68)/365;  break; // Venus
            case 2:age= (value);             break; // Earth
            case 3:age= (value*686.96)/365;  break; // Mars
            case 4:age= (value*11.862);      break; // Jupiter
            case 5:age= (value*29.456);      break; // Saturn
            case 6:age= (value*84.07);       break; // Uranus
            case 7:age= (value*164.81);      break; // Neptune
        }
        return(age);
    }

    /* AGE (DAYS) ON EARTH */
    double calculateDaysAgeOnEarth (double value, int planet)
    {
        double age = 0.0;
        switch(planet)
        {
            case 0:age= (value*58);     break; // Mercury
            case 1:age= (value*243);    break; // Venus
            case 2:age= (value);        break; // Earth
            case 3:age= (value*0.99);   break; // Mars
            case 4:age= (value*0.394);  break; // Jupiter
            case 5:age= (value*0.433);  break; // Saturn
            case 6:age= (value*0.693);  break; // Uranus
            case 7:age= (value*0.645);  break; // Neptune
        }
        return(age);
    }

    /* WEIGHT ON EARTH */
    double calculateWeightOnEarth (double value, int planet)
    {
        double weight = 0.0;
        switch(planet)
        {
            case 0:weight= (value/0.38);  break; // Mercury
            case 1:weight= (value/0.91);  break; // Venus
            case 2:weight= (value);       break; // Earth
            case 3:weight= (value/0.38);  break; // Mars
            case 4:weight= (value/2.36);  break; // Jupiter
            case 5:weight= (value/0.91);  break; // Saturn
            case 6:weight= (value/0.89);  break; // Uranus
            case 7:weight= (value/1.12);  break; // Neptune
        }
        return(weight);
    }

    /* AGE (YEARS) ON OTHER PLANETS */
    double calculateYearsAgeOnOther (double resultOnEarth, int planet)
    {
        double age = 0.0;
        switch(planet)
        {
            case 0:age= (resultOnEarth*365)/87.96;   break; // Mercury
            case 1:age= (resultOnEarth*365)/224.68;  break; // Venus
            case 2:age= (resultOnEarth);             break; // Earth
            case 3:age= (resultOnEarth*365)/686.96;  break; // Mars
            case 4:age= (resultOnEarth/11.862);      break; // Jupiter
            case 5:age= (resultOnEarth/29.456);      break; // Saturn
            case 6:age= (resultOnEarth/84.07);       break; // Uranus
            case 7:age= (resultOnEarth/164.81);      break; // Neptune
        }
        return(age);
    }

    /* AGE (DAYS) ON OTHER PLANETS */
    double calculateDaysAgeOnOther (double resultOnEarth, int planet)
    {
        double age = 0.0;
        switch(planet)
        {
            case 0:age= (resultOnEarth/58);     break; // Mercury
            case 1:age= (resultOnEarth/243);    break; // Venus
            case 2:age= (resultOnEarth);        break; // Earth
            case 3:age= (resultOnEarth/0.99);   break; // Mars
            case 4:age= (resultOnEarth/0.394);  break; // Jupiter
            case 5:age= (resultOnEarth/0.433);  break; // Saturn
            case 6:age= (resultOnEarth/0.693);  break; // Uranus
            case 7:age= (resultOnEarth/0.645);  break; // Neptune
        }
        return(age);
    }

    /* WEIGHT ON OTHER PLANETS */
    double calculateWeightOnOther (double resultOnEarth, int secondPlanet)
    {
        double weight = 0.0;
        switch(secondPlanet)
        {
            case 0:weight= (resultOnEarth*0.38);  break; // Mercury
            case 1:weight= (resultOnEarth*0.91);  break; // Venus
            case 2:weight= (resultOnEarth);       break; // Earth
            case 3:weight= (resultOnEarth*0.38);  break; // Mars
            case 4:weight= (resultOnEarth*2.36);  break; // Jupiter
            case 5:weight= (resultOnEarth*0.91);  break; // Saturn
            case 6:weight= (resultOnEarth*0.89);  break; // Uranus
            case 7:weight= (resultOnEarth*1.12);  break; // Neptune
        }
        return(weight);
    }





}












