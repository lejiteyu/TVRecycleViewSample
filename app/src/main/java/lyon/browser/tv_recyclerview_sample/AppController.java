package lyon.browser.tv_recyclerview_sample;

import android.app.Application;

public class AppController extends Application {


    private static AppController appController;
    public static synchronized AppController getInstance() {
        return appController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
    }
}
