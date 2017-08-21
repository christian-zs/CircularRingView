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
//        getWeek(new Date(System.currentTimeMillis()));

        CalendarAdapter adapter = new CalendarAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 7, OrientationHelper.VERTICAL, false);
        mList.setLayoutManager(mLayoutManager);
        mList.setAdapter(adapter);
        adapter.setWeekStartIndex(getWeekStartIndex(getDate().get(0)));
        adapter.setData(getDate());

    }

    /**
     * 获取模拟时间
     */
    private ArrayList<Date> getDate() {
        ArrayList<Date> date = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int a = i + 1;
            date.add(StringToYMDDate("2017-08-0" + a));
        }
        return date;
    }

    /**
     * 日期转换
     *
     * @param str 自定时间格式字符串
     * @return date
     */
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

    /**
     * 获取指定日期为周几
     *
     * @param date 日期
     * @return 周几位置
     */
    public static int getWeekStartIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
}
