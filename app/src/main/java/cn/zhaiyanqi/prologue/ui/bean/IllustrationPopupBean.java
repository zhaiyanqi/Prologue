package cn.zhaiyanqi.prologue.ui.bean;

import android.net.Uri;

public class IllustrationPopupBean {
    private Uri uri;
    private int width = -2;
    private int height = -2;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
