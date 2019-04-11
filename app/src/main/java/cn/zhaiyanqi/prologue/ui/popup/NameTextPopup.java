package cn.zhaiyanqi.prologue.ui.popup;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;
import com.orhanobut.hawk.Hawk;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import androidx.annotation.NonNull;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.widget.RichStrokeTextView;
import cn.zhaiyanqi.prologue.utils.HawkKey;

public class NameTextPopup extends CenterPopupView {

    private TextView tvCancel, tvConfirm;
    private EditText etName, etFontSize;
    private ImageView ivReduce, ivAdd;
    private int fontSize, fontColor;
    private Switch switchTradition;
    private ConfirmListener confirmListener;

    public NameTextPopup(@NonNull Context context) {
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
        etName = findViewById(R.id.et_name);
        etFontSize = findViewById(R.id.et_font_size);
        ivReduce = findViewById(R.id.btn_reduce);
        ivAdd = findViewById(R.id.btn_add);
        switchTradition = findViewById(R.id.switch_traditional);
    }

    private void initData() {
        etName.setText(Hawk.get(HawkKey.NAME_TEXT, ""));
        fontSize = Hawk.get(HawkKey.NAME_TEXT_FONT_SIZE, 180);
        etFontSize.setText(String.valueOf(fontSize));
        fontColor = Color.WHITE;
        switchTradition.setChecked(Hawk.get(HawkKey.NAME_TEXT_SWITCH_TRADITION, true));
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
    }

    private void createTextView() {
        App.executeTask(() -> {
            String title = etName.getText().toString();
            if (TextUtils.isEmpty(title)) return;
            RichStrokeTextView textView =
                    new RichStrokeTextView(getContext(), Color.BLACK, Color.WHITE);
            textView.setEms(1);
            textView.setTextColor(fontColor);
            textView.setTypeface(App.fonts.get("武将名"));
            try {
                int size = Integer.parseInt(etFontSize.getText().toString());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                Hawk.put(HawkKey.NAME_TEXT_FONT_SIZE, size);
            } catch (Exception e) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            }
            textView.setPadding(20, 20, 20, 20);
            String title1 = switchTradition.isChecked() ?
                    ChineseConverter.convert(title, ConversionType.S2T, getContext()) : title;
            textView.setText(title1);
            Hawk.put(HawkKey.NAME_TEXT, title1);
            Hawk.put(HawkKey.NAME_TEXT_SWITCH_TRADITION, switchTradition.isChecked());
            if (confirmListener != null) {
                confirmListener.onConfirm(textView);
            }
        });

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_name_text;
    }

    public NameTextPopup setConfirmListener(ConfirmListener l) {
        this.confirmListener = l;
        return this;
    }

    public interface ConfirmListener {
        void onConfirm(TextView textView);
    }
}
