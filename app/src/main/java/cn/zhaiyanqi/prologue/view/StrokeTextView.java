package cn.zhaiyanqi.prologue.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

import androidx.appcompat.widget.AppCompatTextView;
import cn.zhaiyanqi.prologue.R;

/**
 * 用于实现武将名字的外发光、描边效果
 */
public class StrokeTextView extends AppCompatTextView {

    TextPaint m_TextPaint;
    int mInnerColor;
    int mOuterColor;
    private boolean m_bDrawSideLine = true; // 默认采用描边

    public StrokeTextView(Context context, int outerColor, int innerColor) {
        super(context);
        m_TextPaint = this.getPaint();
        this.mInnerColor = innerColor;
        this.mOuterColor = outerColor;
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_TextPaint = this.getPaint();
        //获取自定义的XML属性名称
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        //获取对应的属性值
        this.mInnerColor = a.getColor(R.styleable.StrokeTextView_innerColor, 0xffffff);
        this.mOuterColor = a.getColor(R.styleable.StrokeTextView_outerColor, 0xffffff);
        a.recycle();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        m_TextPaint = this.getPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (m_bDrawSideLine) {
            setTextColorUseReflection(mOuterColor);
            m_TextPaint.setStrokeWidth(5); // 描边宽度
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            m_TextPaint.setFakeBoldText(true); // 外层text采用粗体
            m_TextPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
            super.onDraw(canvas);

            // 描内层，恢复原先的画笔
            setTextColorUseReflection(mInnerColor);
            m_TextPaint.setStrokeWidth(0);
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            m_TextPaint.setFakeBoldText(false);
            m_TextPaint.setShadowLayer(1, 0, 0, 0);

        }
        super.onDraw(canvas);
    }


    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        m_TextPaint.setColor(color);
    }

}
