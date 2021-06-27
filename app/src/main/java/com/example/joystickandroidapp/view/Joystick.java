package com.example.joystickandroidapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.joystickandroidapp.viewmodel.MainViewModel;


public class Joystick extends View implements View.OnTouchListener {

    private int medium_radius;
    private int big_radius;
    private  int little_radius;
    private float x_center;
    private float y_center;
    private float xPosition;
    private float yPosition;
    private Boolean init;


    private MainViewModel myMainViewModel;

    public Joystick(Context context) {
        super(context);
        initJoystick();
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        initJoystick();
    }

    public Joystick(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        initJoystick();
    }

    public void initJoystick() {
        this.big_radius=300;
        this.little_radius=80;
        this.medium_radius = 200;
        init = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint bigCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigCircle.setColor(Color.parseColor("#555555"));
        bigCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint mediumCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mediumCircle.setColor(Color.parseColor("#111111"));
        mediumCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint littleCircle = new Paint();
        littleCircle.setShader(new LinearGradient(xPosition, yPosition, xPosition+little_radius, yPosition+little_radius, Color.parseColor("#777777"), Color.WHITE, Shader.TileMode.MIRROR));
        littleCircle.setStyle(Paint.Style.FILL);
        x_center = (int)(getWidth() / 2) + 50;
        y_center = (int)(getHeight() / 2) + 50;
        canvas.drawCircle((int) x_center, (int) y_center, big_radius,
                bigCircle);
        canvas.drawCircle((int) x_center, (int) y_center, medium_radius,
                mediumCircle);
        if(!init){
            canvas.drawCircle((int) x_center, (int) y_center, little_radius,
                    littleCircle);
            init = true;
        }
        else{
            canvas.drawCircle((int) xPosition, (int) yPosition, little_radius,
                    littleCircle);
        }
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float valueX, valueY=0;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            xPosition = x_center;
            yPosition = y_center;
        }
        else{
            xPosition = event.getX();
            yPosition = event.getY();
            float distance = (float) (Math.sqrt((Math.pow(xPosition-x_center, 2)) + (Math.pow(yPosition-y_center, 2))));

            if(distance > (medium_radius)) {
                xPosition = (xPosition - x_center) * (medium_radius / distance) + x_center;
                yPosition = (yPosition - y_center) * (medium_radius / distance) + y_center;
            }
        }
        invalidate();
        try {
            valueX =0;
            if (xPosition>x_center) {
                valueX = xPosition-x_center;
                valueX= valueX/big_radius;
            }
            if (xPosition<x_center) {
                valueX = x_center-xPosition;
                valueX= -1 * (valueX/big_radius);
            }
            if (xPosition==x_center) {
                valueX =0;
            }
            valueY =0;
            if (yPosition>y_center) {
                valueY = yPosition-y_center;
                valueY= -1* (valueY/big_radius);
            }
            if (yPosition<y_center) {
                valueY = y_center-yPosition;
                valueY= (valueY/big_radius);
            }
            if (yPosition==y_center) {
                valueX =0;
            }
            myMainViewModel.joystickMoved(valueX, valueY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void setMyMainViewModel(MainViewModel myMainViewModel) {
        this.myMainViewModel = myMainViewModel;
    }
}
