package lyon.browser.tv_recyclerview_sample;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private List<String> tabContent;

    public Item() {
        // 初始化橫列文字
        tabContent = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            tabContent.add("tab_" + i);
        }
    }

    public List<String> getTabContent() {
        return tabContent;
    }
}
