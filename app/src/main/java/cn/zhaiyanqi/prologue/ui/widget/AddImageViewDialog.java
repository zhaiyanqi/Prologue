package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import cn.zhaiyanqi.prologue.R;

public class AddImageViewDialog {

    private ScaleType type;
    private AlertDialog.Builder builder;
    private View view;
    private TextView tvPath;
    private ImageView ivImportImage;
    private RadioGroup rbSizeType;
    private TextInputEditText etWidth, etHeight;
    private Uri uri;

    public AddImageViewDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        view = View.inflate(context, R.layout.layout_add_image_view, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()));
        initView();
        initListener();
    }

    private void initView() {
        tvPath = view.findViewById(R.id.tv_image_path);
        ivImportImage = view.findViewById(R.id.iv_set_src_image);
        rbSizeType = view.findViewById(R.id.rb_size);
        etWidth = view.findViewById(R.id.et_width);
        etHeight = view.findViewById(R.id.et_height);
    }

    private void initListener() {
        rbSizeType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_match_parent: {
                    type = ScaleType.MATCH_PARNT;
                    etWidth.setEnabled(false);
                    etHeight.setEnabled(false);
                    break;
                }
                case R.id.rb_warp_context: {
                    type = ScaleType.WARP_CONTENT;
                    etWidth.setEnabled(false);
                    etHeight.setEnabled(false);
                    break;
                }
                case R.id.rb_custom: {
                    type = ScaleType.CUSTOM;
                    etWidth.setEnabled(true);
                    etHeight.setEnabled(true);
                    break;
                }
            }
        });
    }

    public void setImportListener(View.OnClickListener l) {
        if (l != null) {
            ivImportImage.setOnClickListener(l);
        }
    }

    public void show() {
        builder.show();
    }

    public enum ScaleType {
        WARP_CONTENT, MATCH_PARNT, CUSTOM
    }

    public void setPathText(String string) {
        tvPath.setText(string);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        setPathText(uri.getPath());
    }
}
