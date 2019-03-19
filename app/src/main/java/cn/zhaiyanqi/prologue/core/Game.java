package cn.zhaiyanqi.prologue.core;

import android.content.Context;

public class Game {
    private Context context;
    private boolean running;

    public Game(Context context) {
        this.context = context;
    }


    public void destory() {
        context = null;
        running = false;
    }
}
