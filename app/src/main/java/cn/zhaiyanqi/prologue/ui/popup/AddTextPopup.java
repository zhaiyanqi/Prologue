package cn.zhaiyanqi.prologue.ui.popup;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.util.Objects;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;

public class AddTextPopup extends CenterPopupView {

    @BindView(R.id.spinner_font)
    Spinner spinnerFont;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_font_size)
    EditText etFontSize;
    @BindView(R.id.switch_traditional)
    Switch switchTradition;
    private OnCreateTextView l;

    public AddTextPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this, this);
        initFontSpinner();
        etFontSize.setText(String.valueOf(60));
    }

    private void initFontSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, App.fontNameArray);
        spinnerFont.setAdapter(adapter);
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        try {
            String title = etContent.getText().toString();
            if (TextUtils.isEmpty(title)) {
                dismiss();
                return;
            }
            TextView textView = new TextView(getContext());
            String font = (String) spinnerFont.getSelectedItem();
            textView.setTypeface(App.fonts.get(font));
            textView.setText(switchTradition.isChecked() ?
                    ChineseConverter.convert(title, ConversionType.S2T, getContext()) : title);
            int size = Integer.parseInt(etFontSize.getText().toString());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            ViewBean bean = new ViewBean()
                    .setName(etContent.getText().toString())
                    .setView(textView);
            if (l != null) {
                l.onCreate(bean);
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

    @OnClick({R.id.btn_add, R.id.btn_reduce})
    void setFontSize(View view) {
        int size = 60;
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

    public AddTextPopup setConfirmListener(OnCreateTextView l) {
        this.l = l;
        return this;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_text_add;
    }


    public interface OnCreateTextView {
        void onCreate(ViewBean bean);
    }
}
