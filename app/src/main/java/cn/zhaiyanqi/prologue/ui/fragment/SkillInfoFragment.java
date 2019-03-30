package cn.zhaiyanqi.prologue.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillInfoFragment extends BaseMakerFragment {

    private EditText curEdittext;

    public SkillInfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_info, container, false);
        ButterKnife.bind(this, view);
        return view;
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
