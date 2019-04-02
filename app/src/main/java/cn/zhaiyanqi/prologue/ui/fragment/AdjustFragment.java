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
    private String CUR_VIEW_KEY_WIDTH = null;
    private String CUR_VIEW_KEY_HEIGHT = null;

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

    @OnClick({R.id.rb_title, R.id.rb_name, R.id.rb_hp, R.id.rb_group, R.id.rb_skill_board,
            R.id.rb_skill_1_bar, R.id.rb_skill_1_name, R.id.rb_skill_1_info,
            R.id.rb_skill_2_bar, R.id.rb_skill_2_name, R.id.rb_skill_2_info,
            R.id.rb_skill_3_bar, R.id.rb_skill_3_name, R.id.rb_skill_3_info,})
    void changeCurView(View view) {
        llChangeHeight.setVisibility(View.GONE);
        llChangeWidth.setVisibility(View.GONE);
        curView = null;
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
            case R.id.rb_group: {
                curView = activity.getCmGroup();
                CUR_VIEW_KEY_MARGIN_LEFT = TemplateFragment.KEY_GROUP_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TemplateFragment.KEY_GROUP_MARGIN_TOP;
                CUR_VIEW_KEY_WIDTH = TemplateFragment.KEY_GROUP_WIDTH;
                CUR_VIEW_KEY_HEIGHT = TemplateFragment.KEY_GROUP_HEIGHT;
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.rb_skill_board: {
                curView = activity.getCmSkillBoard();
                CUR_VIEW_KEY_MARGIN_LEFT = TemplateFragment.KEY_SKILL_BOARD_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TemplateFragment.KEY_SKILL_BOARD_MARGIN_TOP;
                CUR_VIEW_KEY_WIDTH = TemplateFragment.KEY_SKILL_BOARD_WIDTH;
                CUR_VIEW_KEY_HEIGHT = TemplateFragment.KEY_SKILL_BOARD_HEIGHT;
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.rb_skill_1_bar: {
                curView = activity.getCmSkill1Bar();
                CUR_VIEW_KEY_MARGIN_LEFT = TemplateFragment.KEY_SKILL_1_BAR_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TemplateFragment.KEY_SKILL_1_BAR_MARGIN_TOP;
                CUR_VIEW_KEY_WIDTH = TemplateFragment.KEY_SKILL_1_BAR_WIDTH;
                CUR_VIEW_KEY_HEIGHT = TemplateFragment.KEY_SKILL_1_BAR_HEIGHT;
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.rb_skill_2_bar: {
                curView = activity.getCmSkill2Bar();
                CUR_VIEW_KEY_MARGIN_LEFT = TemplateFragment.KEY_SKILL_2_BAR_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TemplateFragment.KEY_SKILL_2_BAR_MARGIN_TOP;
                CUR_VIEW_KEY_WIDTH = TemplateFragment.KEY_SKILL_2_BAR_WIDTH;
                CUR_VIEW_KEY_HEIGHT = TemplateFragment.KEY_SKILL_2_BAR_HEIGHT;
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.rb_skill_3_bar: {
                curView = activity.getCmSkill3Bar();
                CUR_VIEW_KEY_MARGIN_LEFT = TemplateFragment.KEY_SKILL_3_BAR_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = TemplateFragment.KEY_SKILL_3_BAR_MARGIN_TOP;
                CUR_VIEW_KEY_WIDTH = TemplateFragment.KEY_SKILL_3_BAR_WIDTH;
                CUR_VIEW_KEY_HEIGHT = TemplateFragment.KEY_SKILL_3_BAR_HEIGHT;
                llChangeHeight.setVisibility(View.VISIBLE);
                llChangeWidth.setVisibility(View.VISIBLE);
                break;
            }

            case R.id.rb_skill_1_name: {
                curView = activity.getCmSkill1Name();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_1_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_1_MARGIN_TOP;
                break;
            }
            case R.id.rb_skill_2_name: {
                curView = activity.getCmSkill2Name();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_2_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_2_MARGIN_TOP;
                break;
            }
            case R.id.rb_skill_3_name: {
                curView = activity.getCmSkill3Name();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_3_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_3_MARGIN_TOP;
                break;
            }

            case R.id.rb_skill_1_info: {
                curView = activity.getCmSkill1Text();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_1_INFO_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_1_INFO_MARGIN_TOP;
                break;
            }
            case R.id.rb_skill_2_info: {
                curView = activity.getCmSkill2Text();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_2_INFO_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_2_INFO_MARGIN_TOP;
                break;
            }
            case R.id.rb_skill_3_info: {
                curView = activity.getCmSkill3Text();
                CUR_VIEW_KEY_MARGIN_LEFT = SkillInfoFragment.KEY_SKILL_3_INFO_MARGIN_LEFT;
                CUR_VIEW_KEY_MARGIN_TOP = SkillInfoFragment.KEY_SKILL_3_INFO_MARGIN_TOP;
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
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) curView.getLayoutParams();
            switch (view.getId()) {
                case R.id.btn_high: {
                    layoutParams.height += moveStepOffset;
                    Hawk.put(CUR_VIEW_KEY_HEIGHT, layoutParams.height);
                    break;
                }
                case R.id.btn_low: {
                    layoutParams.height -= moveStepOffset;
                    Hawk.put(CUR_VIEW_KEY_HEIGHT, layoutParams.height);
                    break;
                }
                case R.id.btn_fat: {
                    layoutParams.width += moveStepOffset;
                    Hawk.put(CUR_VIEW_KEY_WIDTH, layoutParams.width);
                    break;
                }
                case R.id.btn_thin: {
                    layoutParams.width -= moveStepOffset;
                    Hawk.put(CUR_VIEW_KEY_WIDTH, layoutParams.width);
                    break;
                }
            }
            tvWidth.setText(String.valueOf(layoutParams.width));
            tvHeight.setText(String.valueOf(layoutParams.height));
            curView.requestLayout();
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
