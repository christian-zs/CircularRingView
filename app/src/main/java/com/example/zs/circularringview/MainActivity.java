package com.example.zs.circularringview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zs.circularringview.view.CircularRingView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private CircularRingView mCircularRingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircularRingView = (CircularRingView) findViewById(R.id.view);
        mCircularRingView.setCenterDate(new Date());
        mCircularRingView.setSignDateRecords(getSignRecord());
    }

    private ArrayList<Float> getSignRecord() {
        ArrayList<Float> signRecords = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            signRecords.add((float) (20 + i * 46));
        }
        return signRecords;
    }
}
