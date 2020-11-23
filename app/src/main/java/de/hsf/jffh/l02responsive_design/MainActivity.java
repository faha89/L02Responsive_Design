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

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "hsfMainActivity";    //Erzeugt TAG für logs

    private Button buttonStart;
    private EditText editTextStartzeit;
    private TextView textViewZeit;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private String strTime;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");        //Log für Methodenaufruf

        setContentView(R.layout.activity_main);
        //setzt Screen Orientation auf Portrait in der Manifest
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Initialisierung des Buttons, Typecast auf "Button" //TODO cast redundant?
        buttonStart = (Button) findViewById(R.id.buttonStart);
        editTextStartzeit = (EditText) findViewById(R.id.editTextStartzeit);
        textViewZeit = (TextView) findViewById(R.id.textViewZeit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonStart.setOnClickListener(new View.OnClickListener() {            //Erstellt listener für Buttonklick
            @Override
            public void onClick(View v) {
                strTime = editTextStartzeit.getText().toString();

                //checkt, ob input leer ist und gibt feedback
                if (strTime.isEmpty()) {
                    Log.d(TAG, "empty input");
                    textViewZeit.setText("Zahl eingeben!");
                    progressBar.setProgress(0);
                } else {
                    if (getResources().getString(R.string.button_Start).equals(buttonStart.getText())) {
                        progressBar.setMax(Integer.valueOf(strTime));
                        progressBar.setProgress(Integer.valueOf(strTime));
                        countDownTimer = new CountDownTimer((Integer.valueOf(strTime) * 1000 + 50), 1000) {
                            int count = 0;
                            public void onTick(long millisUntilFinished) {
                                Log.v(TAG, "CountDownTimer.onTick(): sUntilFinished: " + (millisUntilFinished) / 1000);
                                //Log.d(TAG, "CountDownTimer Startwert: " + strTime);
                                textViewZeit.setText(millisUntilFinished / 1000 + "s");
                                if (count > 0) {
                                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                                }
                                Log.v(TAG, "counter: " + count);
                                count++;
                            }
                            public void onFinish() {
                                Log.v(TAG, "CountDownTimer.onFinish()");
                                //countDownTimer.cancel();
                                Log.v(TAG, "counter: " + count);
                                textViewZeit.setText(0 + "s");
                                progressBar.setProgress(0);
                                count = 0;
                                countDownTimer.cancel();
                                buttonStart.setText(getString(R.string.button_Alarm));
                                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm_1);
                                mediaPlayer.setLooping(true);
                                mediaPlayer.start();
                            }
                        };
                        countDownTimer.start();
                        editTextStartzeit.setEnabled(false);
                        buttonStart.setText(R.string.button_Stop);
                        Log.d(TAG, "buttonStart(): START ");
                    } else if (getResources().getString(R.string.button_Stop).equals(buttonStart.getText())) {
                        countDownTimer.cancel();
                        buttonStart.setText(R.string.button_Start);
                        editTextStartzeit.setEnabled(true);
                        Log.d(TAG, "buttonStart(): STOP ");
                    } else if (getResources().getString(R.string.button_Alarm).equals(buttonStart.getText())) {
                        buttonStart.setText(R.string.button_Start);
                        Log.d(TAG, "buttonStart(): STOP ALARM ");
                        mediaPlayer.stop();
                        editTextStartzeit.setEnabled(true);
                    }
                }
                Log.d(TAG, "buttonStart.OnClickListener()");
            }
        });
    }
}
