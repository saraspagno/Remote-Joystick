package com.example.joystickandroidapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.joystickandroidapp.R;
import com.example.joystickandroidapp.databinding.ActivityMainBinding;
import com.example.joystickandroidapp.viewmodel.MainViewModel;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public MainViewModel myMainViewModel;
    Joystick myJoystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getSupportActionBar()!=null)
            this.getSupportActionBar().hide();
        ActivityMainBinding act = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //MainViewModel myMainViewModel = new MainViewModel("Enter IP Address");
    }

    public void connect_click(View v) {
        EditText mIP_address = (EditText)findViewById(R.id.ip_address);
        EditText mPort = (EditText)findViewById(R.id.port);
        this.myMainViewModel = new MainViewModel(mIP_address.getText().toString(), mPort.getText().toString());
        SeekBar seek_rudder = (SeekBar)findViewById(R.id.rudder_id);
        SeekBar seek_throttle = (SeekBar)findViewById(R.id.throttle_id);
        setSeekBar(seek_rudder, -1);
        setSeekBar(seek_throttle, 0);
        this.myJoystick = findViewById(R.id.joystick);
        this.myJoystick.myMainViewModel = this.myMainViewModel;
        //Log.d("CHECK", mEdit.getText().toString());
    }


    public void setSeekBar(SeekBar current_seek, int min) {
        current_seek.setMax((int)((1-min) / 0.01));
        current_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = min + (progress * 0.01);
                Log.d("Joystick", "VALUE: "+value);
                try {
                    if (min == -1) {
                        myMainViewModel.RudderChanged(value);
                    } else {
                        myMainViewModel.ThrottleChanged(value);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
