package cn.zhaiyanqi.prologue.ui.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.orhanobut.hawk.Hawk;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;
import cn.zhaiyanqi.prologue.utils.ColorUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillsFragment extends BaseMakerFragment {

    private static final String NAME_FONT_SIZE_KEY = "NAME_FONT_SIZE_KEY";
    private static final String INFO_FONT_SIZE_KEY = "INFO_FONT_SIZE_KEY";
    private static final String NAME_FONT_COLOR_KEY = "NAME_FONT_COLOR_KEY";
    private static final String INFO_FONT_COLOR_KEY = "INFO_FONT_COLOR_KEY";
    private static final String KEY_SKILL_INFO_FONT_INDEX = "KEY_SKILL_INFO_FONT_INDEX";
    private static final String KEY_SKILL_NAME_FONT_INDEX = "KEY_SKILL_NAME_FONT_INDEX";

    private static final int ID_DIALOG_SELECT_NAME_COLOR = 100;
    private static final int ID_DIALOG_SELECT_INFO_COLOR = 101;
    @BindView(R.id.et_name_font_size)
    EditText etNameFontSize;
    @BindView(R.id.et_info_font_size)
    EditText etInfoFontSize;
    @BindView(R.id.spinner_name_font)
    Spinner spNameFont;
    @BindView(R.id.spinner_info_font)
    Spinner spInfoFont;
    @BindView(R.id.tv_name_font_color)
    TextView tvNameFontColor;
    @BindView(R.id.tv_info_font_color)
    TextView tvInfoFontColor;
    @BindView(R.id.cpv_name_panel)
    ColorPanelView cpvNamePanel;
    @BindView(R.id.cpv_info_panel)
    ColorPanelView cpvInfoPanel;
    private int nameFontSize, infoFontSize;
    private int nameFontColor, infoFontColor;

    public SkillsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        nameFontSize = Hawk.get(NAME_FONT_SIZE_KEY, (int) activity.getCmSkill1Name().getTextSize());
        infoFontSize = Hawk.get(INFO_FONT_SIZE_KEY, (int) activity.getCmSkill1Text().getTextSize());
        etNameFontSize.setText(String.valueOf(nameFontSize));
        etInfoFontSize.setText(String.valueOf(infoFontSize));
        nameFontColor = Hawk.get(NAME_FONT_COLOR_KEY, Color.BLACK);
        infoFontColor = Hawk.get(INFO_FONT_COLOR_KEY, Color.BLACK);
        onColorSelected(ID_DIALOG_SELECT_NAME_COLOR, nameFontColor);
        onColorSelected(ID_DIALOG_SELECT_INFO_COLOR, infoFontColor);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        spNameFont.setAdapter(adapter);
        int selection = Hawk.get(KEY_SKILL_NAME_FONT_INDEX, 2);
        spNameFont.setSelection(selection);
        spInfoFont.setAdapter(adapter);
        int selection2 = Hawk.get(KEY_SKILL_INFO_FONT_INDEX, 3);
        spInfoFont.setSelection(selection2);
    }

    @OnTextChanged(R.id.et_name_font_size)
    void setNameFontSize(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            try {
                nameFontSize = Integer.parseInt(text.toString());
                refreshNameSize();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnTextChanged(R.id.et_info_font_size)
    void setInfoFontSize(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            try {
                infoFontSize = Integer.parseInt(text.toString());
                refreshInfoSize();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.btn_reduce_name, R.id.btn_add_name, R.id.btn_reduce_info, R.id.btn_add_info})
    void adjustFontSize(View view) {
        switch (view.getId()) {
            case R.id.btn_reduce_name: {
                nameFontSize--;
                etNameFontSize.setText(String.valueOf(nameFontSize));
                break;
            }
            case R.id.btn_add_name: {
                nameFontSize++;
                etNameFontSize.setText(String.valueOf(nameFontSize));
                break;
            }
            case R.id.btn_reduce_info: {
                infoFontSize--;
                etInfoFontSize.setText(String.valueOf(infoFontSize));
                break;
            }
            case R.id.btn_add_info: {
                infoFontSize++;
                etInfoFontSize.setText(String.valueOf(infoFontSize));
                break;
            }
        }
        etNameFontSize.clearFocus();
        etInfoFontSize.clearFocus();
    }

    private void refreshNameSize() {
        if (nameFontSize > 0) {
            activity.getCmSkill1Name().setTextSize(TypedValue.COMPLEX_UNIT_PX, nameFontSize);
            activity.getCmSkill2Name().setTextSize(TypedValue.COMPLEX_UNIT_PX, nameFontSize);
            activity.getCmSkill3Name().setTextSize(TypedValue.COMPLEX_UNIT_PX, nameFontSize);
            Hawk.put(NAME_FONT_SIZE_KEY, nameFontSize);
        }
    }

    private void refreshInfoSize() {
        if (infoFontSize > 0) {
            activity.getCmSkill1Text().setTextSize(TypedValue.COMPLEX_UNIT_PX, infoFontSize);
            activity.getCmSkill2Text().setTextSize(TypedValue.COMPLEX_UNIT_PX, infoFontSize);
            activity.getCmSkill3Text().setTextSize(TypedValue.COMPLEX_UNIT_PX, infoFontSize);
            Hawk.put(INFO_FONT_SIZE_KEY, infoFontSize);
        }
    }

    @OnItemSelected(R.id.spinner_name_font)
    void setNameFont(int position) {
        if (position >= 0 && position < App.fontNameArray.length) {
            String font = App.fontNameArray[position];
            Typeface typeface = App.fonts.get(font);
            activity.getCmSkill1Name().setTypeface(typeface);
            activity.getCmSkill2Name().setTypeface(typeface);
            activity.getCmSkill3Name().setTypeface(typeface);
            Hawk.put(KEY_SKILL_NAME_FONT_INDEX, position);
        }
    }

    @OnItemSelected(R.id.spinner_info_font)
    void setInfoFont(int position) {
        if (position >= 0 && position < App.fontNameArray.length) {
            String font = App.fontNameArray[position];
            Typeface typeface = App.fonts.get(font);
            activity.getCmSkill1Text().setTypeface(typeface);
            activity.getCmSkill2Text().setTypeface(typeface);
            activity.getCmSkill3Text().setTypeface(typeface);
            Hawk.put(KEY_SKILL_INFO_FONT_INDEX, position);
        }
    }

    @OnClick({R.id.ll_name_font_color, R.id.ll_info_font_color})
    void setColor(View view) {
        switch (view.getId()) {
            case R.id.ll_name_font_color: {
                ColorPickerDialog.newBuilder()
                        .setColor(nameFontColor)
                        .setShowAlphaSlider(true)
                        .setDialogId(ID_DIALOG_SELECT_NAME_COLOR)
                        .show(activity);
                break;
            }
            case R.id.ll_info_font_color: {
                ColorPickerDialog.newBuilder()
                        .setColor(infoFontColor)
                        .setShowAlphaSlider(true)
                        .setDialogId(ID_DIALOG_SELECT_INFO_COLOR)
                        .show(activity);
                break;
            }
        }
    }

    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case ID_DIALOG_SELECT_NAME_COLOR: {
                nameFontColor = color;
                String colorStr = ColorUtil.int2HexColor(color);
                if (colorStr != null) {
                    tvNameFontColor.setText(colorStr);
                }
                cpvNamePanel.setColor(color);
                activity.getCmSkill1Name().setTextColor(color);
                activity.getCmSkill2Name().setTextColor(color);
                activity.getCmSkill3Name().setTextColor(color);
                Hawk.put(NAME_FONT_COLOR_KEY, color);
                break;
            }
            case ID_DIALOG_SELECT_INFO_COLOR: {
                infoFontColor = color;
                String colorStr = ColorUtil.int2HexColor(color);
                if (colorStr != null) {
                    tvInfoFontColor.setText(colorStr);
                }
                cpvInfoPanel.setColor(color);
                activity.getCmSkill1Text().setTextColor(color);
                activity.getCmSkill2Text().setTextColor(color);
                activity.getCmSkill3Text().setTextColor(color);
                Hawk.put(INFO_FONT_COLOR_KEY, color);
                break;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "技能";
    }
}
