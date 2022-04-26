package com.nagel.lab10;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class SensorViewAdapter extends RecyclerView.Adapter<SensorViewAdapter.SensorViewHolder>{

    private final List<Sensor> sensorList;
    private final LayoutInflater layoutInflater;

    public SensorViewAdapter(Context context, List<Sensor> sensorList) {
        this.sensorList = sensorList;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_sensor, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor sensor = sensorList.get(position);
        holder.name.setText(String.format("Name $s", sensor.getName()));
        holder.type.setText(String.format("Type $s", sensorTypeToString(sensor.getType())));
        holder.power.setText(String.format("Power $s", sensor.getPower()));
        holder.range.setText(String.format("Range $s", sensor.getMaximumRange()));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SensorViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView type;
        private final TextView power;
        private final TextView range;

        public SensorViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.txtSensorName);
            type = itemView.findViewById(R.id.txtSensorName);
            power = itemView.findViewById(R.id.txtSensorPower);
            range = itemView.findViewById(R.id.txtSensorRange);
        }

    }


    private String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
            case Sensor.TYPE_PRESSURE:
                return "Orientation";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";
            default:
                return "Unknown " + sensorType;
        }
    }
}
