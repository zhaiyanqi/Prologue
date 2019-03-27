package cn.zhaiyanqi.prologue.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import cn.zhaiyanqi.prologue.R;

/**
 * 用于实现武将名字的外发光、描边效果
 */
public class HeroNameTextView extends AppCompatTextView {

    TextPaint m_TextPaint;
    int mInnerColor;
    int mOuterColor;
    private boolean m_bDrawSideLine = true; // 默认采用描边

    public HeroNameTextView(Context context, int outerColor, int innerColor) {
        super(context);
        initPaint();
        this.mInnerColor = innerColor;
        this.mOuterColor = outerColor;
    }

    public HeroNameTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        //获取自定义的XML属性名称
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeroNameTextView);
        //获取对应的属性值
        this.mInnerColor = a.getColor(R.styleable.HeroNameTextView_innerColor, 0xffffff);
        this.mOuterColor = a.getColor(R.styleable.HeroNameTextView_outerColor, 0xffffff);
        a.recycle();
    }

    public HeroNameTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        initPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
    }

    private void initPaint() {
        m_TextPaint = this.getPaint();
//        m_TextPaint.setStrokeCap(Paint.Cap.ROUND);
//        m_TextPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setTextColor(mInnerColor);
        m_TextPaint.setStrokeWidth(getWidth() / 30.f);
        m_TextPaint.setStyle(Paint.Style.STROKE);
        m_TextPaint.setFakeBoldText(false);
        m_TextPaint.setShadowLayer(0, 0, 40, 0);
        m_TextPaint.setShadowLayer(0.1f, 0, getHeight() / 65.f, Color.BLACK);
        super.onDraw(canvas);

        setTextColor(mOuterColor);
        m_TextPaint.setStrokeWidth(getWidth() / 50.f);
        m_TextPaint.setStyle(Paint.Style.STROKE);
        m_TextPaint.setFakeBoldText(false);
        m_TextPaint.setShadowLayer(0, 0, 40, 0);
        super.onDraw(canvas);

        setTextColor(mInnerColor);
        m_TextPaint.setStrokeWidth(0); // 描边宽度
        m_TextPaint.setStyle(Paint.Style.FILL); // 描边种类
        m_TextPaint.setFakeBoldText(false); // 外层text采用粗体
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