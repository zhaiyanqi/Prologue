package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class HeroTitleTextView extends AppCompatTextView {
    TextPaint m_TextPaint;

    public HeroTitleTextView(Context context) {
        super(context);
        initPaint();
    }

    public HeroTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public HeroTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        m_TextPaint = this.getPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int currentTextColor = getCurrentTextColor();
        m_TextPaint.setStrokeWidth(0); // 描边宽度
        m_TextPaint.setStyle(Paint.Style.FILL); // 描边种类
        super.onDraw(canvas);

        setTextColor(Color.BLACK);
        m_TextPaint.setStrokeWidth(0);
        m_TextPaint.setStyle(Paint.Style.STROKE);
        super.onDraw(canvas);
        setTextColor(currentTextColor);
    }
}
