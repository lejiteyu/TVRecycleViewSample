package lyon.browser.tv_recyclerview_sample.Activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lyon.browser.tv_recyclerview_sample.Adapter.Item;
import lyon.browser.tv_recyclerview_sample.Adapter.ItemAdapter;
import lyon.browser.tv_recyclerview_sample.R;

public class GoRecycleViewActicity extends FragmentActivity {
    String TAG = GoRecycleViewActicity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 產生15列的項目
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 55; i++) {
            itemList.add(new Item("item",30));
        }

        // 設定 Adapter
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);


    }
}
