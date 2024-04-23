package lyon.browser.tv_recyclerview_sample.Activity.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class TVKotlinActivity: AppCompatActivity()  {
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

    private fun setupChannelList() {
        binding.channelList.apply {
            layoutManager = LinearLayoutManager(this@TVKotlinActivity)
            val onChannelItemClickListener = OnChannelItemClickListener{
                onChannelItemClick(it)
            }
            adapter = ChannelAdapter(mainViewModel.channels, onChannelItemClickListener)

        }
    }

    private fun displayFragment(fragment: Fragment) {
        if(fragment!=null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, fragment)
            transaction.commit()
        }
    }

     fun onChannelItemClick(channel: Channel) {
        val fragment = ProgramsFragment.newInstance(channel)
        displayFragment(fragment)
    }
}