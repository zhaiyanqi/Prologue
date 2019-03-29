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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.orhanobut.hawk.Hawk;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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
public class TitleFragment extends BaseMakerFragment {

    private static final String KEY_FONT_INDEX = "card_maker_title_font_index";
    private static final String KEY_TITLE_FONT_SIZE = "card_maker_title_size";
    private static final String KEY_TITLE_CONTENT = "card_maker_title";
    private static final String KEY_AUTO_TRANSFER = "card_maker_title_auto_transfer";
    private int fontSize;
    private TextView titleView;
    private boolean autoTrans2T = true;

    @BindView(R.id.et_font_size)
    EditText etFontSize;
    @BindView(R.id.color_picker)
    View colorPicker;
    @BindView(R.id.et_font_color)
    EditText colorEditText;
    @BindView(R.id.spinner_font)
    Spinner fontSpinner;
    @BindView(R.id.et_title)
    EditText etTitle;

    private int curColor = Color.parseColor("#f4f424");

    public TitleFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        fontSpinner.setAdapter(adapter);
        int selection = Hawk.get(KEY_FONT_INDEX, 1);
        fontSpinner.setSelection(selection);
        fontSize = Hawk.get(KEY_TITLE_FONT_SIZE, (int) titleView.getTextSize());
        etFontSize.setText(String.valueOf(fontSize));
        String title = Hawk.get(KEY_TITLE_CONTENT, "");
        etTitle.setText(title);
    }

    @OnClick(R.id.color_picker)
    void setColor() {
        ColorPickerDialogBuilder
                .with(Objects.requireNonNull(getContext()))
                .setTitle("设置称号颜色")
                .initialColor(curColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("确定", (dialog, selectedColor, allColors) -> {
                    curColor = selectedColor;
                    colorPicker.setBackgroundColor(selectedColor);
                    titleView.setTextColor(selectedColor);
                    String colorStr = ColorUtil.int2HexColor(selectedColor);
                    if (colorStr != null) {
                        colorEditText.setText(colorStr);
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .build()
                .show();
    }

    @OnItemSelected(R.id.spinner_font)
    void setFont(int position) {
        if (position >= 0 && position < App.fontNameArray.length) {
            String font = App.fontNameArray[position];
            Typeface typeface = App.fonts.get(font);
            titleView.setTypeface(typeface);
            Hawk.put(KEY_FONT_INDEX, position);
        }
    }

    @OnTextChanged(R.id.et_title)
    void setName(CharSequence name) {
        if (titleView != null) {
            if (!TextUtils.isEmpty(name)) {
                String str = autoTrans2T ?
                        ChineseConverter.convert(name.toString(), ConversionType.S2T, activity)
                        : name.toString();
                titleView.setText(str);
                Hawk.put(KEY_TITLE_CONTENT, str);
            } else {
                titleView.setText("");
                Hawk.put(KEY_TITLE_CONTENT, "");
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
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                Hawk.put(KEY_TITLE_FONT_SIZE, fontSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnTextChanged(R.id.et_font_color)
    void setColor(CharSequence color) {
        if (titleView != null) {
            if (!TextUtils.isEmpty(color)) {
                try {
                    int colorInt = Color.parseColor(color.toString());
                    colorPicker.setBackgroundColor(colorInt);
                    titleView.setTextColor(colorInt);
                    curColor = colorInt;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnTextChanged(R.id.et_font_padding)
    void setFontSpacing(CharSequence spacing) {
        if (titleView != null) {
            if (!TextUtils.isEmpty(spacing)) {
                try {
                    int spacings = Integer.parseInt(spacing.toString());
                    titleView.setLineSpacing(spacings, 1);
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
            if (titleView != null && !TextUtils.isEmpty(str = titleView.getText().toString())) {
                str = autoTrans2T ?
                        ChineseConverter.convert(str, ConversionType.S2T, activity)
                        : ChineseConverter.convert(str, ConversionType.T2S, activity);
                titleView.setText(str);
            }
        }
    }

    @OnCheckedChanged(R.id.cb_horizontal_name)
    void setHorizontalName(boolean checked) {
        if (checked) {
            titleView.setEms(10);
        } else {
            titleView.setEms(1);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (activity != null) {
            titleView = activity.getCmTitle();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (titleView != null) {
            titleView = null;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "称号";
    }
}
