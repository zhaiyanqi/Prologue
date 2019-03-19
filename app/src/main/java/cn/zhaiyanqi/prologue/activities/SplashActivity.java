package cn.zhaiyanqi.prologue.activities;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import cn.zhaiyanqi.prologue.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity {

    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        subscribe = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribe(l -> delayAction());
    }

    private void delayAction() {
        startActivity(new Intent(this, HeroMakerActivity.class));
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }
}
