package com.example.trailgage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Control extends AppCompatActivity {

    private TextView mTextViewAngleLeft;
    private TextView mTextViewStrengthLeft;

    private TextView mTextViewAngleRight;
    private TextView mTextViewStrengthRight;
    private TextView mTextViewCoordinateRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);


        mTextViewAngleRight = findViewById(R.id.textView_angle_right);
        mTextViewStrengthRight = findViewById(R.id.textView_strength_right);
        mTextViewCoordinateRight = findViewById(R.id.textView_coordinate_right);

        final JoystickView joystickRight = findViewById(R.id.joystickView_right);
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                angle = setAngel(angle);

                mTextViewAngleRight.setText(angle + "");
                mTextViewStrengthRight.setText(strength + "");
                mTextViewCoordinateRight.setText(
                        String.format("x%03d:y%03d",
                                joystickRight.getNormalizedX(),
                                joystickRight.getNormalizedY())
                );
            }
        });
    }

    private int setAngel(int angle) {
        int angel = 0;
        if (angle >= 337.5 || angle < 22.5) {
            //right angle
            angel = 70;
        } else if (angle >= 22.5 && angle < 67.5) {
            //up-right angle
            angel = 45;
        } else if (angle >= 67.5 && angle < 112.5) {
            //up angle
            angel = 0;
        } else if (angle >= 112.5 && angle < 157.5) {
            // up-left angel
            angel = -45;
        } else if (angle >= 157.5 && angle < 202.5) {
            //Left
            angel = -70;
        } else if (angle >= 202.5 && angle < 247.5) {
            // reverse left
            angel = -135;
        } else if (angle >= 247.5 && angle < 292.5) {
            //reverse
            angel = 0;
        } else if (angle >= 292.5 && angle < 337.5) {
            angel=135;
        }
        return angel;
    }
}

