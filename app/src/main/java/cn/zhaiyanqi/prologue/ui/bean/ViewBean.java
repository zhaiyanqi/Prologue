package cn.zhaiyanqi.prologue.ui.bean;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

public class ViewBean {

    private View view;
    private String name;
    private int resId;
    private Uri uri;
    private int width;
    private int height;
    private int order;
    private ImageView.ScaleType scaleType;

    public ViewBean() {
    }

    public ViewBean(String name, View view) {
        this.name = name;
        this.view = view;
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

    public int getOrder() {
        return order;
    }

    public ViewBean setOrder(int order) {
        this.order = order;
        return this;
    }
}
