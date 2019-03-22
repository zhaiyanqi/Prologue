package cn.zhaiyanqi.prologue.cardmaker;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
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
import cn.zhaiyanqi.prologue.utils.ColorUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {
    @BindView(R.id.color_picker)
    View colorPicker;
    @BindView(R.id.et_font_color)
    EditText colorEditText;
    @BindView(R.id.spinner_font)
    Spinner fontSpinner;
    private CardMakerActivity activity;
    private TextView titleView;
    private boolean autoTrans2T;
    private int curColor = Color.WHITE;

    public TitleFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        fontSpinner.setAdapter(adapter);
        return view;
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
            } else {
                titleView.setText("");
            }
        }
    }

    @OnTextChanged(R.id.et_font_size)
    void setFontSize(CharSequence size) {
        if (titleView != null) {
            if (!TextUtils.isEmpty(size)) {
                int s = Integer.parseInt(size.toString());
                titleView.setTextSize(s);
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
        if (context instanceof CardMakerActivity) {
            activity = (CardMakerActivity) context;
            titleView = activity.cmTitle;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (activity != null) {
            activity = null;
        }
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
