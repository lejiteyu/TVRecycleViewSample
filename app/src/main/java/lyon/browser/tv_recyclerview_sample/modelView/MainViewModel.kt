package lyon.browser.tv_recyclerview_sample.modelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.modelObject.Channel
import lyon.browser.tv_recyclerview_sample.modelObject.ChannelCategory
import lyon.browser.tv_recyclerview_sample.modelObject.Program


class MainViewModel  : ViewModel() {
    private val _textData = MutableLiveData<String>()
    val textData: LiveData<String> = _textData

    init {
        _textData.value = "Hello, MVVM!"
    }

    fun updateText(newText: String) {
        _textData.value = newText
    }

    val channels = listOf(
        Channel("推薦", R.drawable.home_icon),
        Channel("電影", R.drawable.movie_icon),
        Channel("戲劇", R.drawable.drama_icon),
        Channel("動漫", R.drawable.animation_icon),
        Channel("綜藝", R.drawable.variety_show_icon),
        Channel("排行榜", R.drawable.rank_icon),
        Channel("其他", R.drawable.other)
    )

    val channelCategories = listOf(
        ChannelCategory("推薦", listOf(Program("推薦1"), Program("推薦2"), Program("推薦3"), Program("推薦4"))),
        ChannelCategory("電影", listOf(Program("電影1"), Program("電影2"), Program("電影3"))),
        ChannelCategory("戲劇", listOf(Program("戲劇1"), Program("戲劇2"), Program("戲劇3"), Program("戲劇4"), Program("戲劇5"))),
        ChannelCategory("動漫", listOf(Program("動漫1"), Program("動漫2"), Program("動漫3"), Program("動漫4"))),
        ChannelCategory("綜藝", listOf(Program("綜藝1"), Program("綜藝2"), Program("綜藝3"))),
        ChannelCategory("排行榜", listOf(Program("排行榜1"), Program("排行榜2"), Program("排行榜3"))),
        ChannelCategory("其他", listOf(Program("其他1"), Program("其他2"), Program("其他3")))
    )

    fun getProgramsForCategory(categoryName: String): List<Program>? {
        val category = channelCategories.find { it.name == categoryName }
        return category?.programs
    }
}