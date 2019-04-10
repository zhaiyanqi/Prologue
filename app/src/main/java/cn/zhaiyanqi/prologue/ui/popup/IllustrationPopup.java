package cn.zhaiyanqi.prologue.ui.popup;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.IllustrationPopupBean;
import cn.zhaiyanqi.prologue.ui.callback.Callable;

public class IllustrationPopup extends CenterPopupView {

    private TextView tvCancel, tvConfirm, tv_image_path;
    private ImageView ivSelectImage;
    private Callable selectImageCallable;
    private IllustrationPopupBean bean;
    private OnConfirmListener confirmListener;

    public IllustrationPopup(@NonNull Context context) {
        super(context);
        bean = new IllustrationPopupBean();
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
        ivSelectImage = findViewById(R.id.iv_select_img);
        tv_image_path = findViewById(R.id.tv_image_path);
    }

    private void initData() {

    }

    private void initListener() {
        tvConfirm.setOnClickListener(v -> {
            if (confirmListener != null) {
                confirmListener.confirm(bean);
            }
            dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            dismiss();
        });
        ivSelectImage.setOnClickListener(v -> {
            if (selectImageCallable != null) {
                selectImageCallable.call();
            }
        });
    }

    public IllustrationPopup setSelectImageListener(Callable callable) {
        selectImageCallable = callable;
        return this;
    }

    public IllustrationPopup setConfirmListener(OnConfirmListener l) {
        confirmListener = l;
        return this;
    }

    public void setUri(Uri uri) {
        bean.setUri(uri);
        tv_image_path.setText(uri.getPath());
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_illustration;
    }

    public interface OnConfirmListener {
        void confirm(IllustrationPopupBean bean);
    }
}
