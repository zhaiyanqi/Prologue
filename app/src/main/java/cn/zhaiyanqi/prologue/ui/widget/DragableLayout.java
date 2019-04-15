package cn.zhaiyanqi.prologue.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.hawk.Hawk;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.zhaiyanqi.prologue.utils.HawkKey;

public class DragableLayout extends ConstraintLayout {

    private ViewDragHelper dragHelper;
    private OnViewSelected l;
    private boolean showAnimation;

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

    public void setViewSelectedListener(OnViewSelected l) {
        this.l = l;
    }

    private void initCallback() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new Callback());
        showAnimation = Hawk.get(HawkKey.SHOW_TOUCH_SCALE_ANIMATION, true);
    }

    public void setShowAnimation(boolean showAnimation) {
        this.showAnimation = showAnimation;
        Hawk.put(HawkKey.SHOW_TOUCH_SCALE_ANIMATION, showAnimation);
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

    public interface OnViewSelected {
        void onSelect(View view);
    }

    private class Callback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            if (showAnimation) {
                AnimatorSet setDown = new AnimatorSet();
                setDown.playTogether(
                        ObjectAnimator.ofFloat(capturedChild, "scaleX", 1f, 1.1f),
                        ObjectAnimator.ofFloat(capturedChild, "scaleY", 1f, 1.1f),
                        ObjectAnimator.ofFloat(capturedChild, "alpha", 1f, 0.6f)
                );
                setDown.start();
            }
            if (l != null) {
                l.onSelect(capturedChild);
            }
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
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = view.getLeft();
            params.topMargin = view.getTop();
            view.requestLayout();
            invalidate();
            if (showAnimation) {
                AnimatorSet setUp = new AnimatorSet();
                setUp.playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f),
                        ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f),
                        ObjectAnimator.ofFloat(view, "alpha", 0.6f, 1f)
                );
                setUp.start();
            }
        }
    }
}
