package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.R;

public class ColorPickerPopup extends CenterPopupView
        implements ColorPickerView.OnColorChangedListener {

    @BindView(R.id.color_picker_view)
    ColorPickerView colorPicker;
    @BindView(R.id.color_panel_old)
    ColorPanelView oldColorPanel;
    @BindView(R.id.color_panel_new)
    ColorPanelView newColorPanel;
    @BindView(R.id.et_hex)
    EditText etHex;
    private int initColor;
    private int newColor;
    private boolean showAlphaSlider = true;
    private boolean fromEditText;
    private OnColorSelectedListener l;

    public ColorPickerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this, this);
        initView();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_color_picker;
    }

    private void initView() {
        colorPicker.setAlphaSliderVisible(showAlphaSlider);
        colorPicker.setColor(initColor, true);
        oldColorPanel.setColor(initColor);
        newColorPanel.setColor(initColor);
        setHex(initColor);

        if (!showAlphaSlider) {
            etHex.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }

        colorPicker.setOnColorChangedListener(this);
    }

    private void setHex(int color) {
        if (showAlphaSlider) {
            etHex.setText(String.format("%08X", (color)));
        } else {
            etHex.setText(String.format("%06X", (0xFFFFFF & color)));
        }
    }

    @OnTextChanged(R.id.et_hex)
    void onTextChanged(CharSequence text) {
        if (text.length() == 6 || text.length() == 8) {
            if (etHex.isFocused()) {
                String colorText = text.toString();
                int color = Color.parseColor("#" + colorText);
                if (color != colorPicker.getColor()) {
                    fromEditText = true;
                    colorPicker.setColor(color, true);
                }
            }
        }
    }

    @OnClick({R.id.tv_confirm, R.id.tv_cancel})
    void onFinish(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm: {
                if (l != null) {
                    l.onSelect(newColor);
                }
                dismiss();
                break;
            }
            case R.id.tv_cancel: {
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onColorChanged(int newColor) {
        this.newColor = newColor;
        if (newColorPanel != null) {
            newColorPanel.setColor(newColor);
        }
        if (!fromEditText && etHex != null) {
            setHex(newColor);
            if (etHex.hasFocus()) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etHex.getWindowToken(), 0);
                etHex.clearFocus();
            }
        }
        fromEditText = false;
    }

    public ColorPickerPopup setColorSelectedListener(OnColorSelectedListener l) {
        this.l = l;
        return this;
    }

    public ColorPickerPopup setInitColor(int color) {
        this.initColor = color;
        return this;
    }

    public interface OnColorSelectedListener {
        void onSelect(int color);
    }
}
