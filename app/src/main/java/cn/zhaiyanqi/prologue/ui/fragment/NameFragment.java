package cn.zhaiyanqi.prologue.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.orhanobut.hawk.Hawk;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;
import cn.zhaiyanqi.prologue.ui.widget.HeroNameTextView;
import cn.zhaiyanqi.prologue.utils.ColorUtil;

public class NameFragment extends BaseMakerFragment {

    private static final String KEY_FONT_INDEX = "card_maker_name_font_index";
    private static final String KEY_NAME_FONT_SIZE = "card_maker_name_font_size";
    private static final String KEY_NAME_CONTENT = "card_maker_name_content";
    static final String KEY_MARGIN_LEFT = "card_maker_name_margin_left";
    static final String KEY_MARGIN_TOP = "card_maker_name_margin_top";
    private static final String KEY_AUTO_TRANSFER = "card_maker_name_auto_transfer";

    @BindView(R.id.et_font_size)
    EditText etFontSize;
    private HeroNameTextView nameView;
    private boolean autoTrans2T = true;
    private int curColor = Color.WHITE;
    private int curOuterColor = Color.BLACK;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.color_picker)
    View colorPicker;
    @BindView(R.id.outer_color_picker)
    View outerColorPicker;
    @BindView(R.id.et_font_color)
    EditText colorEditText;
    @BindView(R.id.spinner_font)
    Spinner fontSpinner;
    @BindView(R.id.cb_auto_transfer)
    CheckBox cbAutoTransfer;

    private int fontSize;

    public NameFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        fontSpinner.setAdapter(adapter);
        int selection = Hawk.get(KEY_FONT_INDEX, 0);
        fontSpinner.setSelection(selection);
        fontSize = (int) nameView.getTextSize();
        fontSize = Hawk.get(KEY_NAME_FONT_SIZE, (int) nameView.getTextSize());
        etFontSize.setText(String.valueOf(fontSize));
        String title = Hawk.get(KEY_NAME_CONTENT, "");
        etName.setText(title);
        autoTrans2T = Hawk.get(KEY_AUTO_TRANSFER, true);
        cbAutoTransfer.setChecked(autoTrans2T);

        //从缓存中读取名字位置信息
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) nameView.getLayoutParams();
        layoutParams.leftMargin = Hawk.get(KEY_MARGIN_LEFT, layoutParams.leftMargin);
        layoutParams.topMargin = Hawk.get(KEY_MARGIN_TOP, layoutParams.topMargin);
        nameView.requestLayout();
    }

    @OnClick(R.id.color_picker)
    void setColor() {
        ColorPickerDialogBuilder
                .with(Objects.requireNonNull(getContext()))
                .setTitle("设置武将名颜色")
                .initialColor(curColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("确定", (dialog, selectedColor, allColors) -> {
                    curColor = selectedColor;
                    colorPicker.setBackgroundColor(selectedColor);
                    nameView.setInnerColor(selectedColor);
                    String colorStr = ColorUtil.int2HexColor(selectedColor);
                    if (colorStr != null) {
                        colorEditText.setText(colorStr);
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .build()
                .show();
    }

    @OnClick(R.id.outer_color_picker)
    void setOuterColor() {
        ColorPickerDialogBuilder
                .with(Objects.requireNonNull(getContext()))
                .setTitle("设置边缘发光颜色")
                .initialColor(curOuterColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("确定", (dialog, selectedColor, allColors) -> {
                    curOuterColor = selectedColor;
                    outerColorPicker.setBackgroundColor(selectedColor);
                    nameView.setOuterColor(selectedColor);
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .build()
                .show();
    }

    @OnItemSelected(R.id.spinner_font)
    void setFont(int position) {
        if (position >= 0 && position < App.fontNameArray.length) {
            String font = App.fontNameArray[position];
            Typeface typeface = App.fonts.get(font);
            nameView.setTypeface(typeface);
            Hawk.put(KEY_FONT_INDEX, position);
        }
    }

    @OnTextChanged(R.id.et_name)
    void setName(CharSequence name) {
        if (nameView != null) {
            if (!TextUtils.isEmpty(name)) {
                String str = autoTrans2T ?
                        ChineseConverter.convert(name.toString(), ConversionType.S2T, activity)
                        : name.toString();
                nameView.setText(str);
                Hawk.put(KEY_NAME_CONTENT, str);
            } else {
                nameView.setText("");
                Hawk.put(KEY_NAME_CONTENT, "");
            }
        }
    }

    @OnClick({R.id.btn_add, R.id.btn_reduce})
    void setFontSize(View view) {
        switch (view.getId()) {
            case R.id.btn_add: {
                fontSize++;
                etFontSize.setText(String.valueOf(fontSize));
                break;
            }
            case R.id.btn_reduce: {
                fontSize--;
                etFontSize.setText(String.valueOf(fontSize));
                break;
            }
        }
    }

    @OnTextChanged(R.id.et_font_size)
    void setFontSize(CharSequence size) {
        if (!TextUtils.isEmpty(size)) {
            try {
                fontSize = Integer.parseInt(size.toString());
                nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                Hawk.put(KEY_NAME_FONT_SIZE, fontSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnTextChanged(R.id.et_font_color)
    void setColor(CharSequence color) {
        if (nameView != null) {
            if (!TextUtils.isEmpty(color)) {
                try {
                    int colorInt = Color.parseColor(color.toString());
                    colorPicker.setBackgroundColor(colorInt);
                    nameView.setInnerColor(colorInt);
                    curColor = colorInt;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnTextChanged(R.id.et_font_padding)
    void setFontSpacing(CharSequence spacing) {
        if (nameView != null) {
            if (!TextUtils.isEmpty(spacing)) {
                try {
                    int spacings = Integer.parseInt(spacing.toString());
                    nameView.setLineSpacing(spacings, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnCheckedChanged(R.id.cb_auto_transfer)
    void setTransferState(boolean checked) {
        if (autoTrans2T != checked) {
            autoTrans2T = checked;
            String str;
            if (nameView != null && !TextUtils.isEmpty(str = nameView.getText().toString())) {
                str = autoTrans2T ?
                        ChineseConverter.convert(str, ConversionType.S2T, activity)
                        : ChineseConverter.convert(str, ConversionType.T2S, activity);
                nameView.setText(str);
            }
            Hawk.put(KEY_AUTO_TRANSFER, autoTrans2T);
        }
    }

    @OnCheckedChanged({R.id.cb_layer_1, R.id.cb_layer_2, R.id.cb_layer_3})
    void switchLayer(CompoundButton view, boolean checked) {
        switch (view.getId()) {
            case R.id.cb_layer_1: {
                nameView.setLayer1(checked);
                break;
            }
            case R.id.cb_layer_2: {
                nameView.setLayer2(checked);
                break;
            }
            case R.id.cb_layer_3: {
                nameView.setLayer3(checked);
                break;
            }
        }
    }

    @OnCheckedChanged(R.id.cb_horizontal_name)
    void setHorizontalName(boolean checked) {
        if (checked) {
            nameView.setEms(10);
        } else {
            nameView.setEms(1);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (activity != null && activity.getCmName() instanceof HeroNameTextView) {
            nameView = (HeroNameTextView) activity.getCmName();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (nameView != null) {
            nameView = null;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "武将名";
    }
}