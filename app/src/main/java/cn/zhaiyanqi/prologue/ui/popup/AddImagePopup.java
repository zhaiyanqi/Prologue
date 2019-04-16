package cn.zhaiyanqi.prologue.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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
import cn.zhaiyanqi.prologue.ui.callback.Callable;

@SuppressLint("ViewConstructor")
public class AddImagePopup extends CenterPopupView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_width)
    EditText etWidth;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.rg_scale_type)
    RadioGroup rgScaleType;
    @BindView(R.id.tv_image_path)
    TextView tvImagePath;
    private Uri uri;
    private Callable selectImageCallable;
    private OnCreateImageView listener;


    public AddImagePopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this, this);
        tvTitle.setText("添加图片");
    }


    @OnCheckedChanged({R.id.rb_fixed, R.id.rb_fit, R.id.rb_full_screen, R.id.rb_fixed_height_fit_width, R.id.rb_fixed_width_fit_height})
    void setScaleType(CompoundButton view, boolean checked) {
        if (checked) {
            switch (view.getId()) {
                case R.id.rb_fixed: {
                    etWidth.setText(String.valueOf(1));
                    etHeight.setText(String.valueOf(1));
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
                    setEdittext(etWidth, false);
                    setEdittext(etHeight, false);
                    break;
                }
                case R.id.rb_fixed_height_fit_width: {
                    etWidth.setText(String.valueOf(ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    etHeight.setText(String.valueOf(1));
                    setEdittext(etWidth, false);
                    setEdittext(etHeight, true);
                    break;
                }
                case R.id.rb_fixed_width_fit_height: {
                    etWidth.setText(String.valueOf(1));
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
            ImageView view = new ImageView(getContext());
            String name = etName.getText().toString();
            int width = Integer.parseInt(etWidth.getText().toString());
            int height = Integer.parseInt(etHeight.getText().toString());
            int wrapContent = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            ImageView.ScaleType scaleType;
            if (width * height < 0 && (width == wrapContent || height == wrapContent)) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
                view.setAdjustViewBounds(true);
            } else {
                scaleType = ImageView.ScaleType.FIT_XY;
            }
            ViewBean bean = new ViewBean()
                    .setView(view)
                    .setWidth(width)
                    .setHeight(height)
                    .setUri(uri)
                    .setScaleType(scaleType)
                    .setName(name);

            if (listener != null) {
                listener.onCreate(bean);
            }
            dismiss();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.tv_cancel)
    void cancel() {
        dismiss();
    }

    @OnClick(R.id.iv_select_img)
    void selectImage() {
        if (selectImageCallable != null) {
            selectImageCallable.call();
        }
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        tvImagePath.setText(uri.getPath());
    }

    public AddImagePopup setConfirmListener(OnCreateImageView l) {
        this.listener = l;
        return this;
    }

    public AddImagePopup setSelectImageListener(Callable callable) {
        selectImageCallable = callable;
        return this;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_image_add;
    }

    public interface OnCreateImageView {
        void onCreate(ViewBean bean);
    }

}
