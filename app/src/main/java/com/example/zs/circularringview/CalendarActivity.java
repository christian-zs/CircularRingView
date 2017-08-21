package com.example.zs.circularringview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:日历考勤
 * Created by zs on 2017/8/18.
 */

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        getWeek(new Date(System.currentTimeMillis()));

        CalendarAdapter adapter = new CalendarAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 7, OrientationHelper.VERTICAL, false);
        mList.setLayoutManager(mLayoutManager);
        mList.setAdapter(adapter);
        adapter.setData(getDate());

    }

    private ArrayList<Date> getDate() {
        ArrayList<Date> date = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int a = i + 1;
            date.add(StringToYMDDate("2017-08-0" + a));
        }
        return date;
    }

    public static Date StringToYMDDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }
}
