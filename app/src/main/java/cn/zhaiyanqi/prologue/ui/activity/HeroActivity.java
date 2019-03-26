package cn.zhaiyanqi.prologue.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.yl.recyclerview.listener.OnScrollListener;
import com.yl.recyclerview.wrapper.LoadMoreWrapper;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.bean.HeroBean;
import cn.zhaiyanqi.prologue.ui.adapter.HeroAdapter;

public class HeroActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HeroAdapter adapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<HeroBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        initView();
        ActionBar actionBar =
                getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.activity_hero_recycler_view);


        mDataList = LitePal.limit(3 * 5).find(HeroBean.class);
        adapter = new HeroAdapter(mDataList);
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mLoadMoreWrapper);

        // Set the load more listener
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING);

                if (mDataList.size() < 152) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(() -> {
                                getData();
                                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_COMPLETE);

                            });
                        }
                    }, 1000);
                } else {
                    // Show loading end
                    mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void getData() {
        mDataList.addAll(LitePal.limit(3 * 6).find(HeroBean.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
