package lyon.browser.tv_recyclerview_sample.Adapter;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private List<String> tabContent;

    public Item(String sub , int num) {
        // 初始化橫列文字
        tabContent = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            tabContent.add(sub+"_" + i);
        }
    }

    public List<String> getTabContent() {
        return tabContent;
    }
}
