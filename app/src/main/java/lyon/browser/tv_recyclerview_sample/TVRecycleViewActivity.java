package lyon.browser.tv_recyclerview_sample;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class TVRecycleViewActivity extends FragmentActivity {

    String TAG = TVRecycleViewActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    ProgressBar progressBar;
    View view;
    TVSearchFragment tvSearchFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        view=findViewById(R.id.search_fragment);
        view.setBackgroundResource(R.color.transparent);
        view.setBackground(getResources().getDrawable(R.color.transparent));
        progressBar=(ProgressBar)findViewById(R.id.search_progressbar);
        progressBar.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        tvSearchFragment = (TVSearchFragment) fragmentManager.findFragmentById(R.id.search_fragment);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(itemAdapter!=null){
            itemAdapter.onkeyDown(keyCode,event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
