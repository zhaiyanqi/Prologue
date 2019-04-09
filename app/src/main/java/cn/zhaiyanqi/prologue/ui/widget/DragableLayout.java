package cn.zhaiyanqi.prologue.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

public class DragableLayout extends ConstraintLayout {

    private ViewDragHelper dragHelper;

    public DragableLayout(Context context) {
        super(context);
        initCallback();
    }

    public DragableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCallback();
    }

    public DragableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCallback();
    }


    private void initCallback() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new Callback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    private class Callback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public void onViewReleased(@NonNull View view, float xvel, float yvel) {
            view.setX(view.getLeft());
            view.setY(view.getTop());
            invalidate();
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }
    }
}
