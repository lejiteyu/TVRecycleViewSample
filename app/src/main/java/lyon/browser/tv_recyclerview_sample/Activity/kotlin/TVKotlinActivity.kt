package lyon.browser.tv_recyclerview_sample.Activity.kotlin

import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.ChannelAdapter
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.ChannelAdapter.OnChannelItemClickListener
import lyon.browser.tv_recyclerview_sample.Fragment.kotlin.DefaultFragment
import lyon.browser.tv_recyclerview_sample.Fragment.kotlin.ProgramsFragment
import lyon.browser.tv_recyclerview_sample.modelView.MainViewModel
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.databinding.KotlinMainActivityBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel

/**
 * 注意資料夾名稱不可以用MVVM, DataBinding 會找不到
 */

class TVKotlinActivity: FocusControTool()  {
    lateinit var mainViewModel: MainViewModel

    lateinit var binding: KotlinMainActivityBinding// 如果你的佈局layout文件名稱是 activity_main.xml，那麼生成的綁定類別名稱就是 ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=
            DataBindingUtil.setContentView(this, R.layout.kotlin_main_activity)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this // Important for LiveData updates to be observed


        // Optionally, observe LiveData for changes in the TextView's text

        // Optionally, observe LiveData for changes in the TextView's text
        mainViewModel.textData.observe(this, { newText ->
            // Update UI or take any action upon data change
        })

        setupChannelList()
        displayFragment(DefaultFragment())
    }
    private var mLastFocusedPosition = -1
    private fun setupChannelList() {
        binding.menuRecycleView.apply {
            layoutManager = LinearLayoutManager(this@TVKotlinActivity)

            menulayoutManager = layoutManager as LinearLayoutManager
            if(menulayoutManager!=null)
                mainViewModel.updateMenu(menulayoutManager!!)
            val onChannelItemClickListener = OnChannelItemClickListener{ channel, position ->
                expandedChannelList()// 焦點在 ChannelList 上時，展開 ChannelList
                if(mLastFocusedPosition==position)
                    return@OnChannelItemClickListener
                mLastFocusedPosition = position

                onChannelItemClick(channel)

            }

            val onChannelExpanded = ChannelAdapter.OnChannelExpanded{

            }

            val onChannelCollapse = ChannelAdapter.OnChannelCollapse{
                collapseChannelList() // 焦點不在 ChannelList 上時，收縮 ChannelList
                (fragment as ProgramsFragment).setRowFocus(rowPos,itemPos)
            }

            adapter = ChannelAdapter(
                mainViewModel.channels,
                onChannelItemClickListener,
                onChannelExpanded,
                onChannelCollapse
            )

        }
        binding.menuRecycleView.setOnFocusChangeListener { view, b ->
            if(b){
                expandedChannelList()

                Toast.makeText(this,"menu bar focus.",Toast.LENGTH_LONG).show()
            }
        }
    }

    private var isChannelListExpanded = true // 記錄 ChannelList 是否展開
    private fun collapseChannelList() {
        if (isChannelListExpanded) {
            // 如果 ChannelList 是展開的，將其收縮
            val layoutParams = binding.menuRecycleView.layoutParams as RelativeLayout.LayoutParams
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.collapsed_channel_list_width)

            binding.menuRecycleView.layoutParams = layoutParams
            isChannelListExpanded = false
        }
    }
    private fun expandedChannelList() {
        if (!isChannelListExpanded) {
            // 如果 ChannelList 是展開的，將其收縮
            val layoutParams = binding.menuRecycleView.layoutParams as RelativeLayout.LayoutParams
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.channel_list_width)
            binding.menuRecycleView.layoutParams = layoutParams
            isChannelListExpanded = true
        }
    }

    var fragment:Fragment? = null
    private fun displayFragment(fragment: Fragment) {
        if(fragment!=null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, fragment)
            transaction.commit()
        }
    }
     var rowPos:Int?=0
    var itemPos:Int?=0
    fun setRowPos(rowPos:Int){
        this.rowPos=rowPos
    }
    fun setItemPos(itemPos:Int){
        this.itemPos=itemPos
    }
     fun onChannelItemClick(channel: Channel) {
         fragment = ProgramsFragment.newInstance(channel)
        displayFragment(fragment!!)
         // 创建一个实现了 MenuFocusReq 接口的对象
         val menuFocusReqImpl = object : ProgramsFragment.MenuFocusReq {
             override fun focus(rowPos: Int, itemPos: Int) {
                 setRowPos(rowPos)
                 setItemPos(itemPos)
                 println("Row position: $rowPos, Item position: $itemPos")
                 val recyclerView =binding.menuRecycleView
                 // 获取指定位置的 ViewHolder
                 val viewHolder = recyclerView.findViewHolderForAdapterPosition(mLastFocusedPosition)
                 // 如果 ViewHolder 不为空，请求焦点
                 viewHolder?.itemView?.requestFocus()

             }
         }
         (fragment as ProgramsFragment).setMenuFosusReq (menuFocusReqImpl)
     }

}