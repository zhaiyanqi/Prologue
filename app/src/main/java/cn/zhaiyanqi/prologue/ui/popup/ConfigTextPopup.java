package cn.zhaiyanqi.prologue.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.lxj.xpopup.core.CenterPopupView;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import cn.zhaiyanqi.prologue.ui.widget.RichStrokeTextView;
import cn.zhaiyanqi.prologue.utils.ColorUtil;
import cn.zhaiyanqi.prologue.utils.HawkKey;


@SuppressLint("ViewConstructor")
public class ConfigTextPopup extends CenterPopupView {

    private ViewBean bean;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_font_size)
    EditText etFontSize;
    @BindView(R.id.switch_vertical)
    Switch switchVertical;
    @BindView(R.id.switch_rich_text)
    Switch switchRichText;
    @BindView(R.id.et_word_spacing)
    EditText etWordSpacing;
    @BindView(R.id.et_font_spacing)
    EditText etFontSpacing;
    @BindView(R.id.tv_font_color)
    TextView tvFontColor;
    @BindView(R.id.cpv_color_panel)
    ColorPanelView cpvFontColor;
    @BindView(R.id.grid_rich_text)
    GridLayout gridRichText;
    @BindView(R.id.spinner_font)
    Spinner spinnerFont;
    @BindView(R.id.et_rotation)
    EditText etRotation;
    @BindView(R.id.switch_traditional)
    Switch switchTraditional;
    @BindView(R.id.et_ems)
    EditText etEms;
    @BindView(R.id.switch_ems_enable)
    Switch switchEms;
    private Unbinder binder;
    private EditText curEdittext;
    private Typeface typeface;
    private int color;


    public ConfigTextPopup(@NonNull Context context, ViewBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        binder = ButterKnife.bind(this, this);
        initData();
    }

    private void initData() {
        if (bean.getView() instanceof TextView) {
            TextView textView = (TextView) bean.getView();
            tvTitle.setText(bean.getName());
            etName.setText(bean.getName());
            if (bean.isRichTextMode()) {
                etContent.setText(bean.getRichText());
            } else {
                etContent.setText(textView.getText());
            }
            int[] a = new int[3];
            int[] b = {1, 3, 4};
            int[] c = new int[]{1, 2, 3};
            etFontSize.setText(String.valueOf((int) textView.getTextSize()));
            int ems = textView.getMaxEms();
            switchVertical.setChecked(ems == 1);
            etEms.setText(String.valueOf(ems));
            etEms.setEnabled(bean.isFixedWidth());
            switchEms.setChecked(bean.isFixedWidth());
            etWordSpacing.setText(String.valueOf(textView.getLetterSpacing()));
            etFontSpacing.setText(String.valueOf(textView.getLineSpacingExtra()));
            initFontSpinner(textView);
            color = textView.getCurrentTextColor();
            cpvFontColor.setColor(color);
            String colorStr = ColorUtil.int2HexColor(color);
            if (colorStr != null) {
                tvFontColor.setText(colorStr);
            }

            etRotation.setText(String.valueOf(textView.getRotation()));
            switchRichText.setChecked(bean.isRichTextMode());
        }
    }

    private void initFontSpinner(TextView textView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        spinnerFont.setAdapter(adapter);
        Typeface typeface = textView.getTypeface();
        int selection = -1;
        for (String tfName : App.fonts.keySet()) {
            if (App.fonts.get(tfName) == typeface) {
                for (int i = 0; i < App.fontNameArray.length; i++) {
                    if (tfName.equals(App.fontNameArray[i])) {
                        selection = i;
                    }
                }
            }
        }
        spinnerFont.setSelection(selection);
    }

    @OnClick({R.id.btn_add, R.id.btn_reduce})
    void setFontSize(View view) {
        int size = 10;
        try {
            size = Integer.parseInt(etFontSize.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.btn_add: {
                size++;
                break;
            }
            case R.id.btn_reduce: {
                size--;
                break;
            }
        }
        etFontSize.setText(String.valueOf(size));
    }

    @OnItemSelected(R.id.spinner_font)
    void setFont(int position) {
        if (position >= 0 && position < App.fontNameArray.length) {
            String font = App.fontNameArray[position];
            typeface = App.fonts.get(font);
        }
    }

    @OnClick(R.id.ll_font_color)
    void setFontColor() {
        if (getContext() instanceof FragmentActivity) {
            ColorPickerDialog.newBuilder()
                    .setColor(color)
                    .setShowAlphaSlider(true)
                    .setDialogId(HawkKey.TEXT_COLOR_DIALOG_ID)
                    .show((FragmentActivity) getContext());
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    void doFinish(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel: {
                dismiss();
                break;
            }
            case R.id.tv_confirm: {
                doConfirm();
                break;
            }
        }
    }

    private void doConfirm() {
        if (bean.getView() instanceof TextView) {
            try {
                bean.setName(etName.getText().toString());
                TextView textView = (TextView) bean.getView();
                String content = etContent.getText().toString();
                content = switchTraditional.isChecked() ?
                        ChineseConverter.convert(content, ConversionType.S2T, getContext()) : content;

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        Integer.parseInt(etFontSize.getText().toString()));
                textView.setEms(switchVertical.isChecked() ? 1 : textView.getText().length());
                textView.setLetterSpacing(Float.parseFloat(etWordSpacing.getText().toString()));
                textView.setLineSpacing(Float.parseFloat(etFontSpacing.getText().toString()), 1);
                if (typeface != null) {
                    textView.setTypeface(typeface);
                }
                if (textView instanceof RichStrokeTextView) {
                    ((RichStrokeTextView) textView).setInnerColor(color);
                } else {
                    textView.setTextColor(color);
                }
                textView.setText(switchRichText.isChecked() ? Html.fromHtml(content) : content);
                bean.setRichTextMode(switchRichText.isChecked());
                bean.setRichText(content);
                textView.setRotation(Float.parseFloat(etRotation.getText().toString()));
                bean.setFixedWidth(switchEms.isChecked());
                if (switchEms.isChecked()) {
                    textView.setEms(Integer.parseInt(etEms.getText().toString()));
                }
                textView.requestLayout();
                dismiss();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnCheckedChanged(R.id.switch_rich_text)
    void setRichText(boolean isChecked) {
        gridRichText.setVisibility(isChecked ? VISIBLE : GONE);
    }

    @OnCheckedChanged(R.id.switch_ems_enable)
    void setWidthType(boolean isChecked) {
        etEms.setEnabled(isChecked);
        if (!isChecked) {
            etEms.setText(String.valueOf(-1));
        }
    }

    @OnFocusChange(R.id.et_content)
    void onFocusChanged(EditText view, boolean focused) {
        curEdittext = focused ? view : null;
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

    @OnClick({R.id.btn_bold, R.id.btn_new_line, R.id.btn_space})
    void insertRichText(View view) {
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
            case R.id.btn_space: {
                String text = "&nbsp;";
                if (index < 0 || index >= edit.length()) {
                    edit.append(text);
                } else {
                    edit.insert(index, text);
                }
                break;
            }
        }
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_text_config;
    }

    public void setColor(int color) {
        this.color = color;
        String colorStr = ColorUtil.int2HexColor(color);
        if (colorStr != null && tvFontColor != null) {
            tvFontColor.setText(colorStr);
        }
        if (cpvFontColor != null) {
            cpvFontColor.setColor(color);
        }
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        binder.unbind();
    }
}
