package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class StrokeTextView extends AppCompatTextView {

    private TextPaint textPaint;

    public StrokeTextView(Context context) {
        super(context);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = this.getPaint();
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int currentTextColor = getCurrentTextColor();
        setTextColor(Color.BLACK);
        textPaint.setStrokeWidth(5);
        textPaint.setStyle(Paint.Style.STROKE);
        super.onDraw(canvas);

        setTextColor(currentTextColor);
        textPaint.setStrokeWidth(0);
        textPaint.setStyle(Paint.Style.FILL);
        super.onDraw(canvas);
    }
}
