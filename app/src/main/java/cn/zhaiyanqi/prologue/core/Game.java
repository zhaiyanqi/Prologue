package cn.zhaiyanqi.prologue.core;

import android.app.Activity;
import android.content.Context;

public class Game {
    private Context context;
    private Activity activity;
    private boolean running;

    public Game(Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public void destory() {
        context = null;
        running = false;
    }


    public void runOnUiThread(Runnable r) {
        if (r == null) return;
        if (activity == null || activity.isDestroyed()) return;
        activity.runOnUiThread(r);
    }
}
