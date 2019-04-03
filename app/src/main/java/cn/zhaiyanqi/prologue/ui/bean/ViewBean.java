package cn.zhaiyanqi.prologue.ui.bean;

import android.view.View;

public class ViewBean {

    private View view;
    private String name;

    public ViewBean(String name, View view) {
        this.name = name;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
