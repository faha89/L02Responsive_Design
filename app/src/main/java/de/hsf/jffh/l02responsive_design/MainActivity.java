/*
Fabian Hansen
Jens Foelz
L02_Responsive_Design
Abgabe: 9.11.2020
Funktion: Countdown Timer, Eieruhr
 */

package de.hsf.jffh.l02responsive_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    //Erzeugt TAG für logs
    public static final String TAG = "hsfMainActivity";

    private Button buttonStart;
    private EditText editTextStartzeit;
    private TextView textViewZeit;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private String strTime;
    private MediaPlayer mediaPlayer;
    /*
    private String buttonStatus; // TODO switch case für Status des Buttons
    public static final String stringButtonStart = getResources().getString(R.string.button_Start);
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log für Methodenaufruf
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);
        //setzt Screen Orientation auf Portrait in der Manifest
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Initialisierung des Buttons, Typecast auf "Button" //TODO cast redundant?
        buttonStart = (Button) findViewById(R.id.buttonStart);
        editTextStartzeit = (EditText) findViewById(R.id.editTextStartzeit);
        textViewZeit = (TextView) findViewById(R.id.textViewZeit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Erstellt listener für Buttonklick
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "buttonStart.OnClickListener()");
                strTime = editTextStartzeit.getText().toString();
                //checkt, ob input leer ist und gibt feedback
                if (strTime.isEmpty()) {
                    Log.d(TAG, "empty input");
                    textViewZeit.setText("Zahl eingeben!");
                    progressBar.setProgress(0);
                } else {
                    if (getResources().getString(R.string.button_Start).equals(buttonStart.getText())) {
                    /*
                    switch (buttonStatus) { //TODO switch case für Status des Buttons (start, stop, stop alarm)
                        case (stringButtonStart) :
                     */
                        progressBar.setMax(Integer.valueOf(strTime));
                        progressBar.setProgress(Integer.valueOf(strTime));
                        countDownTimer = new CountDownTimer((Integer.valueOf(strTime) * 1000), 100) {
                            public void onTick(long millisUntilFinished) {
                                Log.v(TAG, "CountDownTimer.onTick(): sUntilFinished: " + (millisUntilFinished) / 1000);
                                //Log.d(TAG, "CountDownTimer Startwert: " + strTime);
                                textViewZeit.setText(1+(int)(Math.ceil(millisUntilFinished / 1000)) + "s");
                                    progressBar.setProgress(1+(int) (Math.ceil(millisUntilFinished / 1000)));
                            }
                            public void onFinish() {
                                Log.v(TAG, "CountDownTimer.onFinish()");
                                textViewZeit.setText(0 + "s");
                                progressBar.setProgress(0);
                                countDownTimer.cancel();
                                buttonStart.setText(getString(R.string.button_Alarm));
                                mediaPlayer.start();
                            }
                        };
                        countDownTimer.start();
                        //Disbabled die Zeiteingabe während der Timer läuft
                        editTextStartzeit.setEnabled(false);
                        buttonStart.setText(R.string.button_Stop);
                        Log.d(TAG, "buttonStart(): START ");
                        //Erstellt Mediaplayer beim drücken des Start Buttons
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm_1);
                        mediaPlayer.setLooping(true);
                    } else if (getResources().getString(R.string.button_Stop).equals(buttonStart.getText())) {
                        countDownTimer.cancel();
                        buttonStart.setText(R.string.button_Start);
                        editTextStartzeit.setEnabled(true);
                        Log.d(TAG, "buttonStart(): STOP ");
                    } else if (getResources().getString(R.string.button_Alarm).equals(buttonStart.getText())) {
                        buttonStart.setText(R.string.button_Start);
                        Log.d(TAG, "buttonStart(): STOP ALARM ");
                        editTextStartzeit.setEnabled(true);
                        if (mediaPlayer!= null) {
                            mediaPlayer.stop();
                            //Löscht ausstehende Befehle an Mediaplayer
                            mediaPlayer.reset();
                            //Gibt Resourcen frei, setzt Mediaplayer in Endstate ("gelöscht")
                            mediaPlayer.release();
                            mediaPlayer= null;
                        }
                    }
                }
            }
        });
    }
}
