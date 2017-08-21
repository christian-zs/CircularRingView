package com.example.zs.circularringview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zs.library.CircularRingView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircularRingView circularRingView = (CircularRingView) findViewById(R.id.view);
        Button button = (Button) findViewById(R.id.calendar_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        circularRingView.setCenterDate(new Date());
        circularRingView.setSignDateRecords(getSignRecord());
    }

    private ArrayList<Float> getSignRecord() {
        ArrayList<Float> signRecords = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            signRecords.add((float) (20 + i * 46));
        }
        return signRecords;
    }
}
