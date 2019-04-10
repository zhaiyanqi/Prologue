package cn.zhaiyanqi.prologue.ui.bean;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import cn.zhaiyanqi.prologue.enums.ImportViewType;

public class ViewBean {

    private View view;
    private String name;
    private int resId;
    private Uri uri;
    private int width;
    private int height;
    private ImageView.ScaleType scaleType;
    private boolean selected;
    private ImportViewType type;

    public ViewBean() {
        this.view = null;
        this.name = null;
        this.resId = -1;
        this.uri = null;
        this.width = 0;
        this.height = 0;
    }


    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public ViewBean setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public View getView() {
        return view;
    }

    public ViewBean setView(View view) {
        this.view = view;
        return this;
    }

    public String getName() {
        return name;
    }

    public ViewBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getResId() {
        return resId;
    }

    public ViewBean setResId(int resId) {
        this.resId = resId;
        return this;
    }

    public Uri getUri() {
        return uri;
    }

    public ViewBean setUri(Uri uri) {
        this.uri = uri;
        this.resId = -1;
        return this;
    }

    public ViewBean setUri(int resId) {
        this.uri = null;
        this.resId = resId;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ViewBean setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ViewBean setHeight(int height) {
        this.height = height;
        return this;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
