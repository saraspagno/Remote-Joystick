package com.example.joystickandroidapp.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.joystickandroidapp.R;
import com.example.joystickandroidapp.model.MainModel;

import java.io.IOException;

public class MainViewModel {

    MainModel myMainModel;

    public MainViewModel(String ip_address, String port)  {
        // TODO change the port
        myMainModel = new MainModel(ip_address, 0);
    }

    public void joystickMoved(double x, double y) throws InterruptedException {
        myMainModel.dispatch_joystick(x,y);
    }

    public void RudderChanged(double value) throws InterruptedException {
        Log.d("RUDDER", "We are in MainViewModel about to send! the value is:"+value);
        myMainModel.dispatch_rudder(value);
    }

    public void ThrottleChanged(double value) throws InterruptedException {
        myMainModel.dispatch_throttle(value);
    }
}
