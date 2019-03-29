package cn.zhaiyanqi.prologue.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdjustFragment extends BaseMakerFragment {

    private CardMakerActivity activity;
    private View curView;
    @BindView(R.id.ll_change_height)
    LinearLayout llChangeHeight;
    @BindView(R.id.ll_change_width)
    LinearLayout llChangeWidth;
    @BindView(R.id.sb_step_length)
    SeekBar sbStepLength;
    @BindView(R.id.tv_width)
    TextView tvWidth;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    private int moveStepOffset = 1;
    private String CUR_VIEW_KEY_MARGIN_LEFT = null;
    private String CUR_VIEW_KEY_MARGIN_TOP = null;

    public AdjustFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);
        ButterKnife.bind(this, view);
        sbStepLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    moveStepOffset = 1;
                } else {
                    moveStepOffset = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return view;
    }

    @OnClick({R.id.rb_title, R.id.rb_name, R.id.rb_hp, R.id.rb_skill_board})
    void changeCurView(View view) {
        llChangeHeight.setVisibility(View.GONE);
        llChangeWidth.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.rb_title: {
                curView = activity.getCmTitle();
                CUR_VIEW_KEY_MARGIN_LEFT = TitleFragment.KEY_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TitleFragment.KEY_MARGIN_TOP;
                break;
            }
            case R.id.rb_name: {
                curView = activity.getCmName();
                CUR_VIEW_KEY_MARGIN_LEFT = NameFragment.KEY_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = NameFragment.KEY_MARGIN_TOP;
                break;
            }
            case R.id.rb_hp: {
                curView = activity.getCmHpLayout();
                CUR_VIEW_KEY_MARGIN_LEFT = HpFragment.KEY_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = HpFragment.KEY_MARGIN_TOP;
                break;
            }
            case R.id.rb_skill_board: {
                curView = activity.getCmSkillBoard();
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }
        }
        tvWidth.setText(String.valueOf(curView.getWidth()));
        tvHeight.setText(String.valueOf(curView.getHeight()));
    }


    @OnClick({R.id.up_arrow, R.id.down_arrow, R.id.left_arrow, R.id.right_arrow})
    void moveView(View view) {
        if (curView != null) {
            switch (view.getId()) {
                case R.id.up_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.topMargin -= moveStepOffset;
                    curView.requestLayout();
                    Hawk.put(CUR_VIEW_KEY_MARGIN_TOP, layoutParams.topMargin);
                    break;
                }
                case R.id.down_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.topMargin += moveStepOffset;
                    curView.requestLayout();
                    Hawk.put(CUR_VIEW_KEY_MARGIN_TOP, layoutParams.topMargin);
                    break;
                }
                case R.id.left_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.leftMargin -= moveStepOffset;
                    curView.requestLayout();
                    Hawk.put(CUR_VIEW_KEY_MARGIN_LEFT, layoutParams.leftMargin);
                    break;
                }
                case R.id.right_arrow: {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
                    layoutParams.leftMargin += moveStepOffset;
                    curView.requestLayout();
                    Hawk.put(CUR_VIEW_KEY_MARGIN_LEFT, layoutParams.leftMargin);
                    break;
                }
            }
        }
    }

    @OnClick({R.id.btn_high, R.id.btn_low, R.id.btn_fat, R.id.btn_thin})
    void adjustView(View view) {
        if (curView != null) {
            switch (view.getId()) {
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
            tvWidth.setText(String.valueOf(curView.getWidth()));
            tvHeight.setText(String.valueOf(curView.getHeight()));
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
