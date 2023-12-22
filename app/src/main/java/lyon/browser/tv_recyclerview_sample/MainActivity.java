package lyon.browser.tv_recyclerview_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchOrbView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    String TAG = MainActivity.class.getSimpleName();
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