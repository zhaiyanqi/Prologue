package cn.zhaiyanqi.prologue.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadExtraFonts();
    }

    @SuppressLint("CheckResult")
    private void loadExtraFonts() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        scanExtraFonts();
                    } else {
                        new Handler().postDelayed(this::delayAction, 500);
                        Toast.makeText(this, R.string.no_permission, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void scanExtraFonts() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/sgs/font";
        File fontDir = new File(path);
        if (!fontDir.exists() || !fontDir.isDirectory()) {
            boolean created = fontDir.mkdirs();
            if (!created) return;
            MediaScannerConnection.scanFile(this, new String[]{fontDir.getPath()}, null, null);
        }
        File[] fonts = fontDir.listFiles(pathname -> pathname.getName().endsWith("ttf"));
        String[] extraFonts = new String[fonts.length];
        for (int i = 0; i < extraFonts.length; i++) {
            String filename = fonts[i].getName();
            extraFonts[i] = filename.substring(0, filename.lastIndexOf("."));
            App.fonts.put(extraFonts[i], Typeface.createFromFile(fonts[i]));
        }
        String[] newArr = new String[extraFonts.length + App.fontNameArray.length];
        System.arraycopy(App.fontNameArray, 0, newArr, 0, App.fontNameArray.length);
        System.arraycopy(extraFonts, 0, newArr, App.fontNameArray.length, extraFonts.length);
        App.fontNameArray = newArr;
        new Handler().postDelayed(this::delayAction, 500);
    }

    private void delayAction() {
        startActivity(new Intent(this, CardMakerActivity.class));
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }
}
