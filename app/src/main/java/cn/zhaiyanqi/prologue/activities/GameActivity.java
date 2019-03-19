package cn.zhaiyanqi.prologue.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.core.Game;


public class GameActivity extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private LinearLayout ll;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initGame();
    }

    private void initGame() {
        game = new Game(this);
    }

    private void initView() {

    }

}
