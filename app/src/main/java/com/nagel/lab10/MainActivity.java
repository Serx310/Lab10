package com.nagel.lab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Random;

//TODO: documentation Sensors https://developer.android.com/guide/topics/sensors/sensors_overview
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextToSpeech toSpeech;
    private TextView txtLeft, txtMiddle, txtRight;
    private SensorViewAdapter sensorViewAdapter;
    private float accelerationValue;
    private float accelerationLast;
    private float shake;

    private final String[] fortunes = new String[]{
            "As I see it, yes.", "Ask again later.", "Better not tell you now.", "Cannot predict now.", "Concentrate and ask again.",
            "Don’t count on it.", "It is certain.", "It is decidedly so.", "Most likely.", "My reply is no.", "My sources say no.",
            "Outlook not so good.", "Outlook good.", "Reply hazy, try again.", "Signs point to yes.", "Very doubtful.", "Without a doubt.",
            "Yes.", "Yes – definitely.", "You may rely on it."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //msg for user
        Toast.makeText(this, "Shake device for fortune!", Toast.LENGTH_LONG).show();
        //initializing variables
        txtLeft = findViewById(R.id.txtLeft);
        txtMiddle = findViewById(R.id.txtMiddle);
        txtRight = findViewById(R.id.txtRight);

        //if texttospeech is available (success) the setting the language to default
        toSpeech = new TextToSpeech(getApplicationContext(), i ->{
            if(i == TextToSpeech.SUCCESS){
                toSpeech.setLanguage(Locale.getDefault());
            }
        });
        //setup accelerometer sensor
        setupSensor();
        listAllSensors();
    }

    private void listAllSensors() {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        RecyclerView list = findViewById(R.id.sensorList);
        list.setLayoutManager(new LinearLayoutManager(this));
        sensorViewAdapter = new SensorViewAdapter(this, deviceSensors);
        list.setAdapter(sensorViewAdapter);
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerationValue = SensorManager.GRAVITY_EARTH;
        accelerationLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //3 values(x,y,z axis)
        float[] values =  sensorEvent.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        Log.i("SensorTag: ", getString(R.string.sensor_value, x, y, z));
        //changes textview background color based on result
        updateTextView(x);
        //get fortune
        getFortune(x, y, z);
    }

    private void getFortune(float x, float y, float z) {
        accelerationLast = accelerationValue;
        accelerationValue = (float) Math.sqrt(x*x+y*y+z*z);
        float delta = accelerationValue - accelerationLast;
        shake = shake *0f + delta;
        if(shake > 12){
            int min = 0;
            int max = fortunes.length;
            int fortune = new Random().nextInt(max+min)+min;
            String fortuneText = fortunes[fortune];
            try{
                Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone alert = RingtoneManager.getRingtone(getApplicationContext(), notify);
                alert.play();
            }catch(Exception e){e.printStackTrace();}
            Log.i("Sensortag: ", fortuneText);
            readFortuneOutLoud(fortuneText);
        }
    }

    private void readFortuneOutLoud(String fortuneText) {
        findViewById(R.id.outLoud).setOnClickListener(view ->{
            toSpeech.speak(fortuneText, TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }

    private void updateTextView(float x) {
        TextView[] views = {txtLeft, txtMiddle, txtRight};
        for (TextView text : views){
            text.setBackgroundColor(Color.WHITE);
        }
        if(x >= 5) txtRight.setBackgroundColor(getColor(R.color.gray_700));
        else if(x <= -5) txtMiddle.setBackgroundColor(getColor(R.color.gray_700));
        else txtLeft.setBackgroundColor(getColor(R.color.gray_700));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}