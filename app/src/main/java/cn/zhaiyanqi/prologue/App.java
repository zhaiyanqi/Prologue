package cn.zhaiyanqi.prologue;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {

    public static HashMap<String, Typeface> fonts;
    public static String[] fontNameArray;
    private static ExecutorService executor;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void executeTask(Runnable runnable) {
        executor.execute(runnable);
    }

    private void loadFonts() {
        fonts = new HashMap<>();
        Resources resources = getResources();
        fontNameArray = resources.getStringArray(R.array.font_spinner_values);
        for (String key : fontNameArray) {
            try {
                fonts.put(key, Typeface.createFromAsset(getAssets(), "fonts/" + key + ".ttf"));
            } catch (Exception e) {
                Log.e("font", e.getMessage());
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Hawk.init(this).build();
        LitePal.initialize(this);
        context = getApplicationContext();
        executor = Executors.newFixedThreadPool(3);
        loadFonts();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        executor.shutdown();
        context = null;
    }
}
