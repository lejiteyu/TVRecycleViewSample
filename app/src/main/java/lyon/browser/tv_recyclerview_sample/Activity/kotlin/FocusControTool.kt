package lyon.browser.tv_recyclerview_sample.Activity.kotlin

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

open class FocusControTool: AppCompatActivity() {

    var menulayoutManager:LinearLayoutManager?=null
    var menuPos = 0
    fun setMenuLayoutManager(mennlayoutManager:LinearLayoutManager){
        this.menulayoutManager=mennlayoutManager
    }

    fun fosusMenu(){
        menulayoutManager?.scrollToPosition(menuPos)
    }
}