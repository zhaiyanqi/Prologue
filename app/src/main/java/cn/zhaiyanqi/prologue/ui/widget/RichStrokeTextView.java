package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class RichStrokeTextView extends AppCompatTextView {

    private TextPaint textPaint;
    private int mInnerColor;
    private int mOuterColor;

    public RichStrokeTextView(Context context, int outerColor, int innerColor) {
        super(context);
        initPaint();
        this.mInnerColor = innerColor;
        this.mOuterColor = outerColor;
    }

    public RichStrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RichStrokeTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        textPaint = this.getPaint();
        textPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setTextColor(mInnerColor);
        textPaint.setStrokeWidth(getWidth() / 25.f);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setShadowLayer(0.1f, 0, getHeight() / 65.f, Color.BLACK);
        super.onDraw(canvas);

        setTextColor(mOuterColor);
        textPaint.setStrokeWidth(getWidth() / 30.f);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setShadowLayer(0, 0, 0, 0);
        super.onDraw(canvas);

        setTextColor(mInnerColor);
        textPaint.setStrokeWidth(0); // 描边宽度
        textPaint.setStyle(Paint.Style.FILL); // 描边种类
        textPaint.setShadowLayer(0, 0, 0, 0);
        super.onDraw(canvas);
    }

    public void setInnerColor(int color) {
        this.mInnerColor = color;
        invalidate();
    }

    public void setOuterColor(int color) {
        this.mOuterColor = color;
        invalidate();
    }
}
