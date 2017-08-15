package com.example.zs.circularringview.view;

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

import com.example.zs.circularringview.R;


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
    // 圆环内部字体颜色
    private int centerTextColor;
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
        circleScaleTextSize = mTypedArray.getDimensionPixelOffset(R.styleable.CircularRing_circleScaleTextSize,
                getDpValue(16));
        circleScaleColor = mTypedArray.getColor(R.styleable.CircularRing_circleScaleColor,
                ContextCompat.getColor(context, R.color.circular_background));
        centerTextSize = mTypedArray.getDimension(R.styleable.CircularRing_centerTextSize, getDpValue(8));
        centerTextColor = mTypedArray.getColor(R.styleable.CircularRing_circleScaleColor,
                ContextCompat.getColor(context, R.color.circle_scale_text));
        isShowScale = mTypedArray.getBoolean(R.styleable.CircularRing_isShowScale, true);

        initView();
        mTypedArray.recycle();
    }

    private void initView() {

        // 圆环中心字体
        centerPaintText = new Paint();
        centerPaintText.setColor(centerTextColor);
        centerPaintText.setTextAlign(Paint.Align.CENTER);
        centerPaintText.setTextSize(centerTextSize);
        centerPaintText.setAntiAlias(true);

        // 刻度
        scalePaint = new Paint();
        scalePaint.setColor(circleScaleColor);
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
        scalePaintText.getTextBounds(getContext().getString(R.string.twelve_time), 0,
                getContext().getString(R.string.twelve_time).length(), bounds);

        if (isShowScale) {
            circleRoundRadio = (getMeasuredWidth() - bounds.height() * 2 - CIRCLE_ROUND_SCALE_INTERVAL * 2) / 4;
        } else {
            circleRoundRadio = circleCenter / 2;
        }

        // 圆环宽度
        circleRoundWidth = circleRoundRadio;
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
        int insideCircleWidth = circleRoundWidth / 5 * 4;
        paint.setStrokeWidth(insideCircleWidth);
        paint.setColor(insideRoundBackgroundColor);
        canvas.drawArc(oval, -90, 360, true, paint);

        // TODO 考勤打卡标记
        paint.setColor(Color.WHITE);
        canvas.drawArc(oval, 90, (float) 2.5, false, paint);

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
