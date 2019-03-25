package cn.zhaiyanqi.prologue.cardmaker;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdjustFragment extends Fragment {

    private CardMakerActivity activity;
    private View curView;
    private int moveStepOffset = 3;

    public AdjustFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.rb_title, R.id.rb_name, R.id.rb_hp, R.id.rb_skill_board})
    void changeCurView(View view) {
        switch (view.getId()) {
            case R.id.rb_title: {
                curView = activity.cmTitle;
                break;
            }
            case R.id.rb_name: {
                curView = activity.cmName;
                break;
            }
            case R.id.rb_hp: {
                curView = activity.cmHpLayout;
                break;
            }
            case R.id.rb_skill_board: {
                curView = activity.cmSkillBoard;
                break;
            }
        }
    }


    @OnClick({R.id.up_arrow, R.id.down_arrow, R.id.left_arrow, R.id.right_arrow,
            R.id.btn_high, R.id.btn_low, R.id.btn_fat, R.id.btn_thin})
    void moveView(View view) {
        if (curView != null) {
            switch (view.getId()) {
                case R.id.up_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.topMargin -= moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.down_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.topMargin += moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.left_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.leftMargin -= moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.right_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.leftMargin += moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.btn_high: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.height += moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.btn_low: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.height -= moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.btn_fat: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.width += moveStepOffset;
                    curView.requestLayout();
                    break;
                }
                case R.id.btn_thin: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.width -= moveStepOffset;
                    curView.requestLayout();
                    break;
                }
            }
        }
    }

    @OnTextChanged(R.id.step_length)
    void onTextChanged(CharSequence text) {
        try {
            int step = Integer.parseInt(text.toString());
            if (step > 0) {
                moveStepOffset = step;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CardMakerActivity) {
            activity = (CardMakerActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "位置调整";
    }
}
