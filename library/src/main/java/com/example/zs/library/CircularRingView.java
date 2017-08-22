package com.example.zs.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Description:自定义圆环view
 * Created by zs on 2017/8/14.
 */

public class CircularRingView extends View {

    // 圆环中心位置
    private int circleCenter;
    // 圆环背景颜色
    private int roundBackgroundColor;
    // 内部圆环背景颜色
    private int insideRoundBackgroundColor;
    // 圆环半径
    private int circleRoundRadio;
    // 圆环宽度
    private int circleRoundWidth;
    // 圆环刻度字体颜色
    private int circleScaleTextColor;
    // 圆环刻度字体大小
    private int circleScaleTextSize;
    // 圆环刻度颜色
    private int circleScaleColor;
    // 圆环内部字体大小
    private float centerTextSize;
    // 圆环内部年月字体大小
    private float centerTextYearMonthSize;
    // 圆环内部字体颜色
    private int centerTextColor;
    // 圆心背景颜色
    private int centerColor;
    // 是否显示刻度
    private boolean isShowScale;
    // 椭圆
    private RectF oval;
    // 圆心画笔
    private Paint centerPaintText;
    // 刻度画笔
    private Paint scalePaint;
    // 刻度字体画笔
    private Paint scalePaintText;
    // 圆环画笔
    private Paint paint;
    // 刻度线长度
    private int scaleLineWidth;
    // 中心日期显示
    private String date = "7";
    // 中心年月
    private String yearsMonth = "2017-07";
    // 打卡时间记录
    private ArrayList<Float> signDateRecords = new ArrayList<>();
    // 时间刻度与圆环间距
    private static final int CIRCLE_ROUND_SCALE_INTERVAL = 20;


    public CircularRingView(Context context) {
        super(context);
    }

    public CircularRingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularRing);
        roundBackgroundColor = mTypedArray.getColor(R.styleable.CircularRing_roundColor,
                ContextCompat.getColor(context, R.color.circular_background));
        insideRoundBackgroundColor = mTypedArray.getColor(R.styleable.CircularRing_insideRoundColor,
                ContextCompat.getColor(context, R.color.inside_circular_background));
        circleScaleTextColor = mTypedArray.getColor(R.styleable.CircularRing_circleScaleTextColor,
                ContextCompat.getColor(context, R.color.circle_scale_text));
        circleScaleTextSize = mTypedArray.getDimensionPixelOffset(
                R.styleable.CircularRing_circleScaleTextSize, getDpValue(16));
        circleScaleColor = mTypedArray.getColor(R.styleable.CircularRing_circleScaleColor,
                ContextCompat.getColor(context, R.color.circular_background));
        centerTextSize = mTypedArray.getDimension(R.styleable.CircularRing_centerTextSize, getDpValue(8));
        centerTextYearMonthSize = mTypedArray.getDimension(
                R.styleable.CircularRing_centerYearMonthTextSize, getDpValue(8));
        centerTextColor = mTypedArray.getColor(R.styleable.CircularRing_circleScaleColor,
                ContextCompat.getColor(context, R.color.circle_scale_text));
        isShowScale = mTypedArray.getBoolean(R.styleable.CircularRing_isShowScale, true);
        centerColor = Color.WHITE;

        initView();
        mTypedArray.recycle();
    }

    private void initView() {

        // 圆环中心字体
        centerPaintText = new Paint();
        centerPaintText.setColor(centerTextColor);
        centerPaintText.setTextAlign(Paint.Align.CENTER);
        centerPaintText.setTextSize(centerTextSize);
        centerPaintText.setStyle(Paint.Style.FILL);
        centerPaintText.setTextAlign(Paint.Align.CENTER);
        centerPaintText.setAntiAlias(true);

        // 刻度
        scalePaint = new Paint();
        scalePaint.setColor(Color.WHITE);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setAntiAlias(true);
        scalePaint.setStrokeWidth(5);

        // 刻度字体画笔
        scalePaintText = new Paint();
        scalePaintText.setColor(Color.BLACK);
        scalePaintText.setStrokeWidth(1);
        scalePaintText.setTextSize(circleScaleTextSize);
        scalePaintText.setStyle(Paint.Style.FILL);
        scalePaintText.setTextAlign(Paint.Align.CENTER);
        scalePaintText.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circleRoundRadio);
        paint.setAntiAlias(true);

        // 用于定义的圆弧的形状和大小的界限
        oval = new RectF();

    }

    /**
     * 设置中心日期显示
     *
     * @param date 中心日期
     */
    public void setCenterDate(Date date) {
        SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.getDefault());
        this.date = dd.format(date);
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        yearsMonth = yyyyMMdd.format(date);
        postInvalidate();
    }

    /**
     * 设置考勤打卡记录
     *
     * @param signDateRecords 中心日期
     */
    public void setSignDateRecords(ArrayList<Float> signDateRecords) {
        if (signDateRecords != null && signDateRecords.size() > 0) {
            this.signDateRecords.clear();
            this.signDateRecords.addAll(signDateRecords);
            postInvalidate();
        }
    }

    /**
     * 设置圆心字体颜色
     *
     * @param color 颜色
     */
    public void setCenterTextColor(int color) {
        centerTextColor = color;
        centerPaintText.setColor(color);
        postInvalidate();
    }

    /**
     * 设置圆心背景颜色
     *
     * @param color 颜色
     */
    public void setCenterColor(int color) {
        centerColor = color;
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(circleCenter, widthMeasureSpec);
        int height = getMySize(circleCenter, heightMeasureSpec);

        int size;
        if (width < height) {
            size = width;
        } else {
            size = height;
        }
        setMeasuredDimension(size, size);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 圆环中心位置
        circleCenter = getMeasuredWidth() / 2;
        // 圆环半径
        Rect bounds = new Rect();
        scalePaintText.getTextBounds(getContext().getString(R.string.zero_time), 0,
                getContext().getString(R.string.zero_time).length(), bounds);

        if (isShowScale) {
            circleRoundRadio = (getMeasuredWidth() - bounds.height() * 2) / 4;
        } else {
            circleRoundRadio = circleCenter / 2;
        }

        // 圆环宽度
        circleRoundWidth = circleRoundRadio / 7 * 4;
        // 刻度线长度
        scaleLineWidth = circleRoundWidth / 5;

        oval.left = circleCenter - circleRoundRadio;
        oval.top = circleCenter - circleRoundRadio;
        oval.right = circleCenter + circleRoundRadio;
        oval.bottom = circleCenter + circleRoundRadio;

        // 圆环背景
        paint.setColor(roundBackgroundColor);
        paint.setStrokeWidth(circleRoundWidth);
        canvas.drawArc(oval, -90, 360, false, paint);


        // 内部圆环宽度
        // 检测是否有打卡记录
        if (signDateRecords != null && signDateRecords.size() > 0) {
            int insideCircleWidth = circleRoundWidth / 5 * 4;
            paint.setStrokeWidth(insideCircleWidth);
            paint.setColor(insideRoundBackgroundColor);
            canvas.drawArc(oval, -90, 360, true, paint);

            for (int i = 0; i < signDateRecords.size(); i++) {
                paint.setColor(Color.WHITE);
                canvas.drawArc(oval, signDateRecords.get(i) - 90, (float) 2.5, false, paint);
            }
        }

        // 是否显示刻度
        if (isShowScale) {
            for (int i = 0; i < 4; i++) {
                canvas.save();
                canvas.rotate(360 / 4 * i, circleCenter, circleCenter);
                canvas.drawLine(circleCenter, circleCenter - circleRoundRadio - circleRoundWidth / 2,
                        circleCenter, circleCenter - circleRoundRadio - circleRoundWidth / 2 + scaleLineWidth, scalePaint);
                canvas.restore();
            }

            // 零点
            String zero = getContext().getString(R.string.zero_time);
            scalePaintText.getTextBounds(zero, 0, zero.length(), bounds);
            canvas.drawText(zero, circleCenter,
                    circleCenter - circleRoundRadio - circleRoundWidth / 2 - CIRCLE_ROUND_SCALE_INTERVAL,
                    scalePaintText);
            // 六点
            String six = getContext().getString(R.string.six_time);
            scalePaintText.getTextBounds(zero, 0, six.length(), bounds);
            canvas.drawText(six,
                    circleCenter + circleRoundRadio + circleRoundWidth / 2 + CIRCLE_ROUND_SCALE_INTERVAL + bounds.width() / 2,
                    circleCenter + bounds.height() / 2, scalePaintText);
            // 十二点
            String twelve = getContext().getString(R.string.twelve_time);
            scalePaintText.getTextBounds(twelve, 0, twelve.length(), bounds);
            canvas.drawText(twelve, circleCenter,
                    circleCenter + circleRoundRadio + circleRoundWidth / 2 + CIRCLE_ROUND_SCALE_INTERVAL + bounds.height() / 2,
                    scalePaintText);
            // 十八点
            String eighteen = getContext().getString(R.string.eighteen_time);
            scalePaintText.getTextBounds(eighteen, 0, eighteen.length(), bounds);
            canvas.drawText(eighteen, circleCenter - circleRoundRadio - circleRoundWidth / 2 - CIRCLE_ROUND_SCALE_INTERVAL - bounds.width() / 2,
                    circleCenter + bounds.height() / 2, scalePaintText);

            // 有刻度显示 圆心内部绘制阴影效果
            Paint paintCenter = new Paint();
            paintCenter.setColor(centerColor);
            canvas.drawCircle(circleCenter, circleCenter,  circleRoundRadio - circleRoundWidth / 2, paintCenter);
        } else {
            // 无刻度显示 圆心内部实心显示
            Paint paintCenter = new Paint();
            paintCenter.setColor(centerColor);
            canvas.drawCircle(circleCenter, circleCenter, circleRoundRadio - circleRoundWidth / 2, paintCenter);
        }

        // 绘制中心日期
        if (isShowScale) {
            centerPaintText.setTextSize(centerTextSize);
            centerPaintText.setColor(centerTextColor);
            centerPaintText.getTextBounds(date, 0, date.length(), bounds);
            canvas.drawText(date, circleCenter,
                    circleCenter, centerPaintText);

            centerPaintText.setTextSize(centerTextYearMonthSize);
            centerPaintText.setColor(ContextCompat.getColor(getContext(), R.color.circle_year_month_text));
            centerPaintText.getTextBounds(yearsMonth, 0, yearsMonth.length(), bounds);
            canvas.drawText(yearsMonth, circleCenter,
                    circleCenter + bounds.height() + CIRCLE_ROUND_SCALE_INTERVAL, centerPaintText);


        } else {
            centerPaintText.getTextBounds(date, 0, date.length(), bounds);
            canvas.drawText(date, circleCenter,
                    circleCenter + bounds.height() / 2, centerPaintText);
        }

    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: { // 如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: { // 如果测量模式是最大取值为size
                // 我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: { // 如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w,
                getContext().getResources().getDisplayMetrics());
    }
}
