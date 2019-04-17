package cn.zhaiyanqi.prologue.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.SeekBarAdapter;
import cn.zhaiyanqi.prologue.ui.bean.PadSettingBean;

@SuppressLint("ViewConstructor")
public class LayoutSettingPopup extends CenterPopupView {

    private int mStep, sStep, width, height;
    private PadSettingBean bean;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;

    private SeekBar sbMoveStep, sbScaleStep;
    private TextView tvCancel, tvConfirm;
    private EditText etMainLayoutWidth, etMainLayoutHeight;

    public LayoutSettingPopup(@NonNull Context context) {
        super(context);
        bean = new PadSettingBean();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        sbMoveStep = findViewById(R.id.sb_move_step);
        sbScaleStep = findViewById(R.id.sb_scale_step);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm = findViewById(R.id.tv_confirm);
        etMainLayoutWidth = findViewById(R.id.et_width);
        etMainLayoutHeight = findViewById(R.id.et_height);
    }

    private void initData() {
        mStep = bean.getMoveStep();
        sStep = bean.getScaleStep();
        sbMoveStep.setProgress(bean.getMoveStep());
        sbScaleStep.setProgress(bean.getScaleStep());
        etMainLayoutWidth.setText(String.valueOf(bean.getMainLayoutWidth()));
        etMainLayoutHeight.setText(String.valueOf(bean.getMainLayoutHeight()));
    }

    private void initListener() {
        sbMoveStep.setOnSeekBarChangeListener(new SeekBarAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStep = progress > 0 ? progress : 1;
            }
        });
        sbScaleStep.setOnSeekBarChangeListener(new SeekBarAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sStep = progress > 0 ? progress : 1;
            }
        });
        tvCancel.setOnClickListener(v -> {
            if (cancelListener != null) cancelListener.onCancel();
            dismiss();
        });
        tvConfirm.setOnClickListener(v -> onConfirm());
    }

    private void onConfirm() {
        bean.setMoveStep(mStep);
        bean.setScaleStep(sStep);
        String sWidth = etMainLayoutWidth.getText().toString();
        if (!TextUtils.isEmpty(sWidth)) {
            try {
                bean.setMainLayoutWidth(Integer.parseInt(sWidth));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String sHeight = etMainLayoutHeight.getText().toString();
        if (!TextUtils.isEmpty(sHeight)) {
            try {
                bean.setMainLayoutHeight(Integer.parseInt(sHeight));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (confirmListener != null) confirmListener.onConfirm(bean);
        dismiss();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_pad_setting;
    }

    public LayoutSettingPopup setMoveStep(int moveStep) {
        bean.setMoveStep(moveStep);
        return this;
    }

    public LayoutSettingPopup setScaleStep(int scaleStep) {
        bean.setScaleStep(scaleStep);
        return this;
    }

    public LayoutSettingPopup setLayoutWidth(int width) {
        bean.setMainLayoutWidth(width);
        return this;
    }

    public LayoutSettingPopup setLayoutHeight(int height) {
        bean.setMainLayoutHeight(height);
        return this;
    }

    public LayoutSettingPopup setConfirmListener(OnConfirmListener l) {
        this.confirmListener = l;
        return this;
    }

    public LayoutSettingPopup setCancelListener(OnCancelListener l) {
        this.cancelListener = l;
        return this;
    }

    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        initData();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnConfirmListener {
        void onConfirm(PadSettingBean bean);
    }
}
