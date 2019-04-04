package cn.zhaiyanqi.prologue.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.zhaiyanqi.prologue.R;

public class AddImageViewDialog {

    private AlertDialog.Builder builder;
    private View view;
    private TextView tvPath;
    private ImageView ivImportImage;
    private RadioGroup rbSizeType;
    private TextInputEditText etWidth, etHeight;
    private Uri uri;
    private int width, height;

    public AddImageViewDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        view = View.inflate(context, R.layout.layout_add_image_view, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setTitle("请选择导入参数");
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
        width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        height = ConstraintLayout.LayoutParams.MATCH_PARENT;
    }

    private void initListener() {
        rbSizeType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_match_parent: {
                    etWidth.setEnabled(false);
                    etHeight.setEnabled(false);
                    width = ConstraintLayout.LayoutParams.MATCH_PARENT;
                    height = ConstraintLayout.LayoutParams.MATCH_PARENT;
                    break;
                }
                case R.id.rb_warp_context: {
                    etWidth.setEnabled(false);
                    etHeight.setEnabled(false);
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    break;
                }
                case R.id.rb_custom: {
                    etWidth.setEnabled(true);
                    etHeight.setEnabled(true);
                    break;
                }
            }
        });
        etWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    width = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    height = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setImportListener(View.OnClickListener l) {
        if (l != null) {
            ivImportImage.setOnClickListener(l);
        }
    }

    public void setPositiveListener(DialogInterface.OnClickListener l) {
        builder.setPositiveButton("确定", l);
    }

    public void show() {
        builder.show();
    }

    private void setPathText(String string) {
        tvPath.setText(string);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        setPathText(uri.getPath());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
