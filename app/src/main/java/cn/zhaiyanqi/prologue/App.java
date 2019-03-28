package cn.zhaiyanqi.prologue;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import org.litepal.LitePal;

import java.util.HashMap;

public class App extends Application {

    public static HashMap<String, Typeface> fonts;
    public static String[] fontNameArray;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
        loadFonts();
        Hawk.init(context).build();
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
}
