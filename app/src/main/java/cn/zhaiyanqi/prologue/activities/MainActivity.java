package cn.zhaiyanqi.prologue.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import cn.zhaiyanqi.prologue.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView btnStartGame;
    private ImageView imgStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        btnStartGame = findViewById(R.id.btn_start_game);
        btnStartGame.setOnClickListener(this);
        imgStart = findViewById(R.id.start_img);

        findViewById(R.id.btn_hero_info).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_game: {
                startActivity(new Intent(this, HeroActivity.class));
//                startTransition(imgStart, HeroActivity.class);
                break;
            }
        }
    }

    public void startTransition(ImageView imageView, Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        imageView,
                        getString(R.string.transition_start_game_img));
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
