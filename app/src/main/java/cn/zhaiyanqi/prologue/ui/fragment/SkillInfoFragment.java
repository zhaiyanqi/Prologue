package cn.zhaiyanqi.prologue.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.orhanobut.hawk.Hawk;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillInfoFragment extends BaseMakerFragment {

    static final String KEY_SKILL_1_MARGIN_LEFT = "KEY_SKILL_1_MARGIN_LEFT";
    static final String KEY_SKILL_1_MARGIN_TOP = "KEY_SKILL_1_MARGIN_TOP";
    static final String KEY_SKILL_1_INFO_MARGIN_LEFT = "KEY_SKILL_1_INFO_MARGIN_LEFT";
    static final String KEY_SKILL_1_INFO_MARGIN_TOP = "KEY_SKILL_1_INFO_MARGIN_TOP";
    static final String KEY_SKILL_1_INFO = "KEY_SKILL_1_INFO";
    static final String KEY_SKILL_1_Name = "KEY_SKILL_1_Name";

    static final String KEY_SKILL_2_MARGIN_LEFT = "KEY_SKILL_2_MARGIN_LEFT";
    static final String KEY_SKILL_2_MARGIN_TOP = "KEY_SKILL_2_MARGIN_TOP";
    static final String KEY_SKILL_2_INFO_MARGIN_LEFT = "KEY_SKILL_2_INFO_MARGIN_LEFT";
    static final String KEY_SKILL_2_INFO_MARGIN_TOP = "KEY_SKILL_2_INFO_MARGIN_TOP";
    static final String KEY_SKILL_2_INFO = "KEY_SKILL_2_INFO";
    static final String KEY_SKILL_2_Name = "KEY_SKILL_2_Name";

    static final String KEY_SKILL_3_MARGIN_LEFT = "KEY_SKILL_3_MARGIN_LEFT";
    static final String KEY_SKILL_3_MARGIN_TOP = "KEY_SKILL_3_MARGIN_TOP";
    static final String KEY_SKILL_3_INFO_MARGIN_LEFT = "KEY_SKILL_3_INFO_MARGIN_LEFT";
    static final String KEY_SKILL_3_INFO_MARGIN_TOP = "KEY_SKILL_3_INFO_MARGIN_TOP";
    static final String KEY_SKILL_3_INFO = "KEY_SKILL_3_INFO";
    static final String KEY_SKILL_3_Name = "KEY_SKILL_3_Name";

    private EditText curEdittext;

    @BindView(R.id.et_skill1_name)
    EditText etSkill1Name;
    @BindView(R.id.et_skill1_info)
    EditText etSkill1Info;
    @BindView(R.id.et_skill2_name)
    EditText etSkill2Name;
    @BindView(R.id.et_skill2_info)
    EditText etSkill2Info;
    @BindView(R.id.et_skill3_name)
    EditText etSkill3Name;
    @BindView(R.id.et_skill3_info)
    EditText etSkill3Info;



    public SkillInfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_info, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ConstraintLayout.LayoutParams skill1Params = (ConstraintLayout.LayoutParams) activity.getCmSkill1Name().getLayoutParams();
        skill1Params.leftMargin = Hawk.get(KEY_SKILL_1_MARGIN_LEFT, skill1Params.leftMargin);
        skill1Params.topMargin = Hawk.get(KEY_SKILL_1_MARGIN_TOP, skill1Params.topMargin);
        activity.getCmSkill1Name().requestLayout();
        ConstraintLayout.LayoutParams skill1InfoParams = (ConstraintLayout.LayoutParams) activity.getCmSkill1Text().getLayoutParams();
        skill1InfoParams.leftMargin = Hawk.get(KEY_SKILL_1_INFO_MARGIN_LEFT, skill1InfoParams.leftMargin);
        skill1InfoParams.topMargin = Hawk.get(KEY_SKILL_1_INFO_MARGIN_TOP, skill1InfoParams.topMargin);
        activity.getCmSkill1Name().requestLayout();

        ConstraintLayout.LayoutParams skill2Params = (ConstraintLayout.LayoutParams) activity.getCmSkill2Name().getLayoutParams();
        skill2Params.leftMargin = Hawk.get(KEY_SKILL_2_MARGIN_LEFT, skill2Params.leftMargin);
        skill2Params.topMargin = Hawk.get(KEY_SKILL_2_MARGIN_TOP, skill2Params.topMargin);
        activity.getCmSkill2Name().requestLayout();
        ConstraintLayout.LayoutParams skill2InfoParams = (ConstraintLayout.LayoutParams) activity.getCmSkill2Text().getLayoutParams();
        skill2InfoParams.leftMargin = Hawk.get(KEY_SKILL_2_INFO_MARGIN_LEFT, skill2InfoParams.leftMargin);
        skill2InfoParams.topMargin = Hawk.get(KEY_SKILL_2_INFO_MARGIN_TOP, skill2InfoParams.topMargin);
        activity.getCmSkill2Text().requestLayout();

        ConstraintLayout.LayoutParams skill3Params = (ConstraintLayout.LayoutParams) activity.getCmSkill3Name().getLayoutParams();
        skill3Params.leftMargin = Hawk.get(KEY_SKILL_3_MARGIN_LEFT, skill3Params.leftMargin);
        skill3Params.topMargin = Hawk.get(KEY_SKILL_3_MARGIN_TOP, skill3Params.topMargin);
        activity.getCmSkill3Name().requestLayout();
        ConstraintLayout.LayoutParams skill3InfoParams = (ConstraintLayout.LayoutParams) activity.getCmSkill3Text().getLayoutParams();
        skill3InfoParams.leftMargin = Hawk.get(KEY_SKILL_3_INFO_MARGIN_LEFT, skill3InfoParams.leftMargin);
        skill3InfoParams.topMargin = Hawk.get(KEY_SKILL_3_INFO_MARGIN_TOP, skill3InfoParams.topMargin);
        activity.getCmSkill3Text().requestLayout();

        etSkill1Name.setText(Hawk.get(KEY_SKILL_1_Name, ""));
        etSkill1Info.setText(Hawk.get(KEY_SKILL_1_INFO, ""));
        etSkill2Name.setText(Hawk.get(KEY_SKILL_2_Name, ""));
        etSkill2Info.setText(Hawk.get(KEY_SKILL_2_INFO, ""));
        etSkill3Name.setText(Hawk.get(KEY_SKILL_3_Name, ""));
        etSkill3Info.setText(Hawk.get(KEY_SKILL_3_INFO, ""));
    }

    @OnTextChanged(R.id.et_skill1_name)
    void setSkill1Name(CharSequence text) {
        activity.getCmSkill1Name().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_1_Name, text.toString());
    }

    @OnTextChanged(R.id.et_skill1_info)
    void setSkill1Text(CharSequence text) {
        activity.getCmSkill1Text().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_1_INFO, text.toString());
    }

    @OnTextChanged(R.id.et_skill2_name)
    void setSkill2Name(CharSequence text) {
        activity.getCmSkill2Name().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_2_Name, text.toString());
    }

    @OnTextChanged(R.id.et_skill2_info)
    void setSkill2Text(CharSequence text) {
        activity.getCmSkill2Text().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_2_INFO, text.toString());
    }

    @OnTextChanged(R.id.et_skill3_name)
    void setSkill3Name(CharSequence text) {
        activity.getCmSkill3Name().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_3_Name, text.toString());
    }

    @OnTextChanged(R.id.et_skill3_info)
    void setSkill3Text(CharSequence text) {
        activity.getCmSkill3Text().setText(Html.fromHtml(text.toString()));
        Hawk.put(KEY_SKILL_3_INFO, text.toString());
    }

    @OnClick({R.id.btn_heart, R.id.btn_spade, R.id.btn_club, R.id.btn_diamond})
    void insertSuit(Button button) {
        if (curEdittext == null) return;
        int index = curEdittext.getSelectionStart();//获取光标所在位置
        String text = button.getText().toString();
        Editable edit = curEdittext.getEditableText();//获取EditText的文字
        if (index < 0 || index >= edit.length()) {
            edit.append(text);
        } else {
            edit.insert(index, text);
        }
    }

    @OnClick({R.id.btn_bold, R.id.btn_new_line})
    void insertBoldText(View view) {
        if (curEdittext == null) return;
        int index = curEdittext.getSelectionStart();//获取光标所在位置
        Editable edit = curEdittext.getEditableText();//获取EditText的文字
        switch (view.getId()) {
            case R.id.btn_bold: {
                String text = "<b>加粗内容</b>";
                if (index < 0 || index >= edit.length()) {
                    edit.append(text);
                } else {
                    edit.insert(index, text);
                }
                break;
            }
            case R.id.btn_new_line: {
                String text = "<br/>";
                if (index < 0 || index >= edit.length()) {
                    edit.append(text);
                } else {
                    edit.insert(index, text);
                }
                break;
            }
        }
    }


    @OnCheckedChanged({R.id.cb_skill1, R.id.cb_skill2, R.id.cb_skill3, R.id.cb_skill4})
    void switchSkills(CompoundButton view, boolean checked) {
        switch (view.getId()) {
            case R.id.cb_skill1: {
                activity.getSkill1Group().setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_skill2: {
                activity.getSkill2Group().setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_skill3: {
                activity.getSkill3Group().setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_skill4: {
                break;
            }
        }
    }

    @OnFocusChange({R.id.et_skill1_name, R.id.et_skill2_name, R.id.et_skill3_name, R.id.et_skill4_name,
            R.id.et_skill1_info, R.id.et_skill2_info, R.id.et_skill3_info, R.id.et_skill4_info,})
    void curEdittext(EditText view, boolean hasFocus) {
        if (hasFocus) {
            curEdittext = view;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "技能信息";
    }
}
