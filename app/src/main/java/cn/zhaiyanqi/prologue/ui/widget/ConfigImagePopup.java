package cn.zhaiyanqi.prologue.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;

@SuppressLint("ViewConstructor")
public class ConfigImagePopup extends CenterPopupView {

    private ViewBean bean;
    private TextView tvTitle;
    private EditText etName, etWidth, etHeight;

    public ConfigImagePopup(@NonNull Context context, ViewBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        initData();
    }

    private void initData() {
        tvTitle.setText(bean.getName());
        etName.setText(bean.getName());
        View view = bean.getView();
        if (view != null) {
            etWidth.setText(String.valueOf(view.getWidth()));
            etHeight.setText(String.valueOf(view.getHeight()));
        }
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        etWidth = findViewById(R.id.et_width);
        etHeight = findViewById(R.id.et_height);
        etName = findViewById(R.id.et_name);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_image_config;
    }
}
