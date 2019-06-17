package com.powerrich.corelib.view.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.powerrich.corelib.R;


/**
 * 文 件 名：CircleProgressBar
 * 描   述：自定义圆形进度条
 * 作   者：Wangzheng
 * 时   间：2018-8-7 10:27:47
 * 版   权：v1.0
 */
public class CircleProgressBar extends View {
    private Paint paint;

    private int mWidth;  //控件的宽度，宽和高相等

    private int roundWidth;   //圆环的宽度

    private int roundColor;  //圆环的颜色

    private int progressColor;   //进度条的颜色

    private int curProgress;   //进度条当前的进度，根据这个进度计算要偏移的多少角度

    private int maxProgress = 100;   //进度条最大的进度。

    private int textSize;   //百分比的字体大小

    private int textColor;  //百分比的字体的颜色

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        paint = new Paint();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        //将圆环的宽度设置为16dp,转换为像素，适应平台
        roundWidth = (int) (typedArray.getDimension(R.styleable.CircleProgressBar_roundWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        roundColor = typedArray.getColor(R.styleable.CircleProgressBar_roundColor, Color.parseColor("#11339ED4"));
        progressColor = typedArray.getColor(R.styleable.CircleProgressBar_progressColor, Color.parseColor("#3F51B5"));
        curProgress = typedArray.getInt(R.styleable.CircleProgressBar_curProgress, 0);
        maxProgress = typedArray.getInt(R.styleable.CircleProgressBar_maxProgress, 100);
        textSize = (int) typedArray.getDimension(R.styleable.CircleProgressBar_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        textColor = typedArray.getColor(R.styleable.CircleProgressBar_textColor, Color.BLACK);
        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置圆心坐标,为控件的宽度的一半
        int centerX = mWidth / 2;
        int centerY = centerX;
        //设置半径
        int radius = centerX - roundWidth / 2;
        //设置画笔颜色
        paint.setColor(roundColor);
        //设置画笔为空心
        paint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //绘制圆形
        canvas.drawCircle(centerX, centerY, radius, paint);

        //绘制圆环进度
        paint.setColor(progressColor);
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        float endDegree = 360 * (float) curProgress / (float) maxProgress;
        //起始角度为最上顶点，
        /**
         *  drawArc(RectF rectF,int startAngle,int sweepAngle,boolean useCenter,Paint paint)
         *  第一个参数：指定圆弧的外轮廓矩形区域
         *  第二个参数；圆弧的起始角度，单位为度
         *  第三个参数：圆弧扫过的角度，顺时针方向，单位为度，从右中间开始为零度，绘制一圈，即要360度，
         *  第四个参数：true表示绘制圆弧将圆心包括在内，通常用来绘制扇形
         *
         * */
        canvas.drawArc(rectF, -90, endDegree, false, paint);


        /**绘制文本信息*/
        paint.setStrokeWidth(0);
        //设置百分比字体颜色
        paint.setColor(textColor);
        //设置百分比字体为粗体
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        //设置百分比字体大小
        paint.setTextSize(textSize);
        //得到文本的总宽度
        float textWidth;
        String result;
        if (curProgress == 100) {
            result = "Done";
            textWidth = paint.measureText(result);
        } else {
            result = curProgress + "%";
            textWidth = paint.measureText(result);
        }
        canvas.drawText(result, centerX - textWidth / 2, centerY + textSize / 2, paint);

    }


    /***
     * 在onMeasure中，如果MeasureSpec的模式是AT_MOST则把宽高设置为80dp,否则，在布局文件中将宽高设置wrap_content，将是和父控件一样大
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(widthSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));
            setMeasuredDimension(mWidth, mWidth);
        } else {
            mWidth = getWidth();
        }

    }

    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setCurProgress(int curProgress) {
        if (curProgress < 0) {
            this.curProgress = 0;
        } else {
            this.curProgress = curProgress;
        }
        invalidate();   //重绘组件

    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
