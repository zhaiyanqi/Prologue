package cn.zhaiyanqi.prologue.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;

@SuppressLint("ViewConstructor")
public class ConfigImagePopup extends CenterPopupView {

    private ViewBean bean;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_width)
    EditText etWidth;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_left)
    EditText etLeft;
    @BindView(R.id.et_top)
    EditText etTop;
    @BindView(R.id.et_rotation)
    EditText etRotation;
    @BindView(R.id.rg_scale_type)
    RadioGroup rgScaleType;

    private TextView tvCancel, tvConfirm;

    public ConfigImagePopup(@NonNull Context context, ViewBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this, this);
        initView();
        initData();
    }

    private void initView() {
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
    }

    private void initData() {
        tvTitle.setText(bean.getName());
        etName.setText(bean.getName());
        View view = bean.getView();
        if (view != null) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            int width = params.width;
            int height = params.height;
            if (width == ConstraintLayout.LayoutParams.WRAP_CONTENT && height == ConstraintLayout.LayoutParams.WRAP_CONTENT) {
                rgScaleType.check(R.id.rb_fit);
            } else if (width == ConstraintLayout.LayoutParams.MATCH_PARENT && height == ConstraintLayout.LayoutParams.MATCH_PARENT) {
                rgScaleType.check(R.id.rb_full_screen);
            } else if (width == ConstraintLayout.LayoutParams.WRAP_CONTENT) {
                rgScaleType.check(R.id.rb_fixed_height_fit_width);
            } else if (height == ConstraintLayout.LayoutParams.WRAP_CONTENT) {
                rgScaleType.check(R.id.rb_fixed_width_fit_height);
            } else {
                rgScaleType.check(R.id.rb_fixed);
            }
            etRotation.setText(String.valueOf(view.getRotation()));
        }
    }

    @OnCheckedChanged({R.id.rb_fixed, R.id.rb_fit, R.id.rb_full_screen, R.id.rb_fixed_height_fit_width, R.id.rb_fixed_width_fit_height})
    void setScaleType(CompoundButton view, boolean checked) {
        if (checked) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) bean.getView().getLayoutParams();
            etLeft.setText(String.valueOf(params.leftMargin));
            etTop.setText(String.valueOf(params.topMargin));
            etLeft.setEnabled(true);
            etTop.setEnabled(true);
            switch (view.getId()) {
                case R.id.rb_fixed: {
                    etWidth.setText(String.valueOf(bean.getView().getWidth()));
                    etHeight.setText(String.valueOf(bean.getView().getHeight()));
                    setEdittext(etWidth, true);
                    setEdittext(etHeight, true);
                    break;
                }
                case R.id.rb_fit: {
                    etWidth.setText(String.valueOf(ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    etHeight.setText(String.valueOf(ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    setEdittext(etWidth, false);
                    setEdittext(etHeight, false);
                    break;
                }
                case R.id.rb_full_screen: {
                    etWidth.setText(String.valueOf(ConstraintLayout.LayoutParams.MATCH_PARENT));
                    etHeight.setText(String.valueOf(ConstraintLayout.LayoutParams.MATCH_PARENT));
                    etLeft.setText(String.valueOf(0));
                    etTop.setText(String.valueOf(0));
                    etLeft.setEnabled(false);
                    etTop.setEnabled(false);
                    setEdittext(etWidth, false);
                    setEdittext(etHeight, false);
                    break;
                }
                case R.id.rb_fixed_height_fit_width: {
                    etWidth.setText(String.valueOf(ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    etHeight.setText(String.valueOf(bean.getView().getHeight()));
                    setEdittext(etWidth, false);
                    setEdittext(etHeight, true);
                    break;
                }
                case R.id.rb_fixed_width_fit_height: {
                    etWidth.setText(String.valueOf(bean.getView().getWidth()));
                    etHeight.setText(String.valueOf(ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    setEdittext(etWidth, true);
                    setEdittext(etHeight, false);
                    break;
                }
            }
        }
    }

    private void setEdittext(EditText edittext, boolean enable) {
        edittext.setFocusable(enable);
        edittext.setFocusableInTouchMode(enable);
        edittext.setEnabled(enable);
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        try {
            if (bean.getView() instanceof ImageView) {
                ImageView view = (ImageView) bean.getView();
                int width = Integer.parseInt(etWidth.getText().toString());
                int height = Integer.parseInt(etHeight.getText().toString());
                int leftMargin = Integer.parseInt(etLeft.getText().toString());
                int topMargin = Integer.parseInt(etTop.getText().toString());
                int wrapContent = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                if (width * height < 0 && (width == wrapContent || height == wrapContent)) {
                    view.setAdjustViewBounds(true);
                } else {
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                params.width = width;
                params.height = height;
                params.leftMargin = leftMargin;
                params.topMargin = topMargin;
                view.setRotation(Float.parseFloat(etRotation.getText().toString()));
                view.requestLayout();
                bean.setName(etName.getText().toString());
                dismiss();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_cancel)
    void cancel() {
        dismiss();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_image_config;
    }
}
