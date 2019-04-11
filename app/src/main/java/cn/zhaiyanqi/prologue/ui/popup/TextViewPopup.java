package cn.zhaiyanqi.prologue.ui.popup;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.lxj.xpopup.core.CenterPopupView;
import com.orhanobut.hawk.Hawk;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.widget.StrokeTextView;
import cn.zhaiyanqi.prologue.utils.ColorUtil;
import cn.zhaiyanqi.prologue.utils.HawkKey;

public class TextViewPopup extends CenterPopupView {

    private TextView tvCancel, tvConfirm, tvFontColor;
    private EditText etName, etFontSize;
    private ImageView ivReduce, ivAdd;
    private LinearLayout llFontColor;
    private int fontSize, fontColor;
    private Switch switchTradition;
    private ConfirmListener confirmListener;
    private ColorPanelView cpvColor;

    public TextViewPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
//        tvFontColor = findViewById(R.id.tv_font_color);
        etName = findViewById(R.id.et_name);
        etFontSize = findViewById(R.id.et_font_size);
        ivReduce = findViewById(R.id.btn_reduce);
        ivAdd = findViewById(R.id.btn_add);
//        llFontColor = findViewById(R.id.ll_font_color);
        switchTradition = findViewById(R.id.switch_traditional);
        cpvColor = findViewById(R.id.cpv_name_panel);
    }

    private void initData() {
        etName.setText(Hawk.get(HawkKey.TITLE_TEXT, ""));
        fontSize = Hawk.get(HawkKey.TITLE_TEXT_FONT_SIZE, 100);
        etFontSize.setText(String.valueOf(fontSize));
        fontColor = Hawk.get(HawkKey.TITLE_TEXT_FONT_COLOR, Color.parseColor("#f4f424"));
        String colorStr = ColorUtil.int2HexColor(fontColor);
        if (colorStr != null) {
            tvFontColor.setText(colorStr);
        }
        cpvColor.setColor(fontColor);
        switchTradition.setChecked(Hawk.get(HawkKey.TITLE_TEXT_SWITCH_TRADITION, true));
    }

    private void initListener() {
        tvConfirm.setOnClickListener(v -> {
            createTextView();
            dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            dismiss();
        });

        ivReduce.setOnClickListener(v -> {
            String text = etFontSize.getText().toString();
            try {
                fontSize = Integer.parseInt(text) - 1;
            } catch (Exception e) {
                fontSize = 1;
            }
            if (fontSize < 1) fontSize = 1;
            etFontSize.setText(String.valueOf(fontSize));
        });
        ivAdd.setOnClickListener(v -> {
            String text = etFontSize.getText().toString();
            try {
                fontSize = Integer.parseInt(text) + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            etFontSize.setText(String.valueOf(fontSize));
        });

        llFontColor.setOnClickListener(v -> {
            if (getContext() instanceof FragmentActivity) {
                ColorPickerDialog.newBuilder()
                        .setColor(fontColor)
                        .setShowAlphaSlider(true)
                        .setDialogId(HawkKey.TITLE_TEXT_DIALOG_ID)
                        .show((FragmentActivity) getContext());
            }
        });
    }

    public void setFontColor(int color) {
        fontColor = color;
        String colorStr = ColorUtil.int2HexColor(color);
        if (colorStr != null && tvFontColor != null) {
            tvFontColor.setText(colorStr);
        }
        if (cpvColor != null) {
            cpvColor.setColor(color);
        }
        Hawk.put(HawkKey.TITLE_TEXT_FONT_COLOR, color);
    }

    private void createTextView() {
        String title = etName.getText().toString();
        if (TextUtils.isEmpty(title)) return;
        StrokeTextView textView = new StrokeTextView(getContext());
        textView.setEms(1);
        textView.setTextColor(fontColor);
        textView.setTypeface(App.fonts.get("称号"));
        textView.setText(switchTradition.isChecked() ?
                ChineseConverter.convert(title, ConversionType.S2T, getContext()) : title);
        try {
            int size = Integer.parseInt(etFontSize.getText().toString());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            Hawk.put(HawkKey.TITLE_TEXT_FONT_SIZE, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (confirmListener != null) {
            confirmListener.onConfirm(textView);
        }
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_text_view;
    }

    public TextViewPopup setConfirmListener(ConfirmListener l) {
        this.confirmListener = l;
        return this;
    }

    public interface ConfirmListener {
        void onConfirm(TextView textView);
    }
}
