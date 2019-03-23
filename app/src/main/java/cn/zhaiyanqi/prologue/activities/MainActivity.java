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
    private ImageView imgStart, imgHeroMaker;

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
        imgHeroMaker = findViewById(R.id.btn_bg_hero_maker);
        findViewById(R.id.btn_hero_info).setOnClickListener(this);
        findViewById(R.id.btn_hero_maker).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_game: {
                startActivity(new Intent(this, HeroActivity.class));

                break;
            }
            case R.id.btn_hero_maker: {
//                startActivity(new Intent(this, HeroMakerActivity.class));
//                startTransition(imgHeroMaker, HeroMakerActivity.class, R.string.transition_hero_maker_img);
            }
        }
    }

    public void startTransition(ImageView imageView, Class<? extends AppCompatActivity> activityClass, int transitionId) {
        Intent intent = new Intent(this, activityClass);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        imageView,
                        getString(transitionId));
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
