package lyon.browser.tv_recyclerview_sample.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lyon.browser.tv_recyclerview_sample.Activity.kotlin.TVKotlinActivity;
import lyon.browser.tv_recyclerview_sample.R;

public class MainActivity extends FragmentActivity {
    String TAG = MainActivity.class.getSimpleName();
    Context context;
    Button goRecycleViewBtn;
    Button goTvRecycleViewBtn;
    Button goTvHorizonzlGridBtn;

    Button goKotlinHorizontalGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        goRecycleViewBtn = findViewById(R.id.go_recycleView);
        goTvRecycleViewBtn = findViewById(R.id.go_tv_RecycleView);
        goTvHorizonzlGridBtn = findViewById(R.id.go_tv_HorizontalGridView);
        goKotlinHorizontalGridView = findViewById(R.id.go_kotlin_HorizontalGridView);

        goRecycleViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoRecycleViewActicity.class);
                startActivity(intent);
            }
        });

        goTvRecycleViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TVRecycleViewActivity.class);
                startActivity(intent);
            }
        });


        goTvHorizonzlGridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TVHorizontalGridViewActivity.class);
                startActivity(intent);
            }
        });


        goKotlinHorizontalGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TVKotlinActivity.class);
                startActivity(intent);
            }
        });
    }

}