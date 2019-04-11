package cn.zhaiyanqi.prologue.ui.popup;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;
import com.orhanobut.hawk.Hawk;

import androidx.annotation.NonNull;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.utils.ColorUtil;
import cn.zhaiyanqi.prologue.utils.HawkKey;

public class TitleTextPopup extends CenterPopupView {


    private TextView tvCancel, tvConfirm, tvFontColor;
    private EditText etName, etFontSize;
    private ImageView ivReduce, ivAdd;
    private LinearLayout llFontColor;
    private int fontSize, fontColor;
    private Switch switchTradition;

    public TitleTextPopup(@NonNull Context context) {
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
        tvFontColor = findViewById(R.id.tv_font_color);
        etName = findViewById(R.id.et_name);
        etFontSize = findViewById(R.id.et_font_size);
        ivReduce = findViewById(R.id.btn_reduce);
        ivAdd = findViewById(R.id.btn_add);
        llFontColor = findViewById(R.id.ll_font_color);
        switchTradition = findViewById(R.id.switch_traditional);
    }

    private void initData() {
        etName.setText(Hawk.get(HawkKey.TITLE_TEXT, ""));
        fontSize = Hawk.get(HawkKey.TITLE_TEXT_FONT_SIZE, 20);
        etFontSize.setText(String.valueOf(fontSize));
        fontColor = Hawk.get(HawkKey.TITLE_TEXT_FONT_COLOR, Color.BLACK);
        String colorStr = ColorUtil.int2HexColor(fontColor);
        if (colorStr != null) {
            tvFontColor.setText(colorStr);
        }
        switchTradition.setChecked(Hawk.get(HawkKey.TITLE_TEXT_SWITCH_TRADITION, true));
    }

    private void initListener() {

        tvConfirm.setOnClickListener(v -> {
            dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            dismiss();
        });


    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_title_text;
    }
}
