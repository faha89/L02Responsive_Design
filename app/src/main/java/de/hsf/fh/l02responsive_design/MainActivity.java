/*
Fabian Hansen
Jens Foelz
L02_Responsive_Design
Abgabe: 9.11.2020
Funktion: Countdown Timer, Eieruhr
 */

package de.hsf.fh.l02responsive_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "hsfMainActivity";    //Erzeugt TAG für logs

    private Button buttonStart;
    private EditText editTextStartzeit;
    private TextView textViewZeit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate()");        //Log für Methodenaufruf

        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);         //setzt Screen Orientation auf Portrait in der Manifest

        buttonStart = (Button) findViewById(R.id.buttonStart);                          //Initialisierung des Buttons, Typecast auf "Button" //TODO cast redundant?
        editTextStartzeit = (EditText) findViewById(R.id.editTextStartzeit);
        textViewZeit = (TextView) findViewById(R.id.textViewZeit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonStart.setOnClickListener(new View.OnClickListener() {            //Erstellt listener für Buttonklick
            @Override
            public void onClick(View v) {
                Log.d(TAG, "buttonStart.OnClickListener()" );
                String str = editTextStartzeit.getText().toString();                 //getText() aus editTextStartzeit und wandelt ihn in String um, übergibt String
                textViewZeit.setText(str + "s");                                     //Setzt Zeitangabe textViewZeit
                buttonStart.setText("Stop");
                if (str.isEmpty()) {                                                 //checkt, ob input leer ist und gibt feedback
                    Log.d(TAG, "buttonStart.OnClickListener(), empty input");
                    textViewZeit.setText("Zahl eingeben!");
                    progressBar.setProgress(0);
                } else {
                    progressBar.setProgress(Integer.valueOf(str));                   //Setzt Fortschritt der ProgressBar, Umwandlung String in Integer //Max auf 99 in xml gesetzt um maximaler Eingabe zu entsprechen
                }

            }
        });
    }
}