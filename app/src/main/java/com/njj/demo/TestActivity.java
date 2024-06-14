package com.njj.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ScanRadar radar = (ScanRadar) findViewById(R.id.scan_radar);

       /* final int[] point_x = {50,50};//getRandomArray(50, 10);

        final int[] point_y = {50,50};//getRandomArray(60, 10);

        radar.setOnPointUpdateListener(new ScanRadar.OnPointUpdateListener() {

            @Override

            public void OnUpdate(Canvas canvas, Paint paintPoint, float cx, float cy,

                                 float radius) {

                for (int i = 0; i < point_y.length; i++) {

                    canvas.drawCircle(cx+point_x[i], cy+point_y[i], 5, paintPoint);

                }

            }

        });*/
        radar.setDirection(-1);
        radar.start();

    }

}