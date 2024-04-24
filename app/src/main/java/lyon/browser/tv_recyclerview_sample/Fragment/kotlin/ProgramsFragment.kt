package lyon.browser.tv_recyclerview_sample.Fragment.kotlin

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.ChannelAdapter
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.HorizontalAdapter
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.ProgramAdapter
import lyon.browser.tv_recyclerview_sample.AppController
import lyon.browser.tv_recyclerview_sample.databinding.FragmentProgramsBinding
import lyon.browser.tv_recyclerview_sample.databinding.KotlinMainActivityBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel
import lyon.browser.tv_recyclerview_sample.modelObject.Program
import lyon.browser.tv_recyclerview_sample.modelView.MainViewModel

class ProgramsFragment : Fragment() {

    private lateinit var binding: FragmentProgramsBinding
    lateinit var mainViewModel: MainViewModel
    companion object {
        private const val ARG_CHANNEL = "channel"

        fun newInstance(channel: Channel): ProgramsFragment {

            val bundle = Bundle().apply {
                this.putParcelable(ARG_CHANNEL, channel)
            }
            return ProgramsFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgramsBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val channel = arguments?.getParcelable<Channel>(ARG_CHANNEL)
        channel?.let {
            updateProgramsList(it)
            val programs=mainViewModel.getProgramsForCategory(it.name)
            if(programs!=null)
                setupProgramsList(programs,it.name, channel)
        }
    }

    private fun updateProgramsList(channel: Channel) {
        // 根據 channel 的信息更新界面，包括圖示和文字
        binding.title.text = channel.name
        binding.icon.setImageDrawable(activity?.resources?.getDrawable(channel.icon))
    }

    fun interface MenuFocusReq{
        fun focus(rowPos:Int, itemPos:Int)
    }
    var menuFocusReq:MenuFocusReq?=null
    fun setMenuFosusReq(menuFocusReq:MenuFocusReq){
        this.menuFocusReq=menuFocusReq;
    }

    private fun setupProgramsList(programs: List<Program>,title:String, channel: Channel) {
        binding.programsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val onRowKeyListener = HorizontalAdapter.OnRowKeyListener { view, keyCode, keyEvent, rowPos, itemPos, itemSize ->
                run{
                    when(keyCode){
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            if(itemPos<=0){
                                menuFocusReq?.focus(rowPos,itemPos)
                                return@OnRowKeyListener true
                            }else{
                                Toast.makeText(context, "HorizontalAdapter["+rowPos+"]["+itemPos+"] KEYCODE_DPAD_LEFT", Toast.LENGTH_LONG)
                                    .show()
                                }
                            }


                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            if(itemPos<itemSize-1){

                            }else{
                                return@OnRowKeyListener true
                            }
                            Toast.makeText(context, "HorizontalAdapter ["+rowPos+"]["+itemPos+"] KEYCODE_DPAD_RIGHT", Toast.LENGTH_LONG)
                                .show()
                        }

                        KeyEvent.KEYCODE_DPAD_UP -> {

                            if(rowPos>0){

                            }else{
                                return@OnRowKeyListener true
                            }
                            Toast.makeText(context, "HorizontalAdapter ["+rowPos+"]["+itemPos+"] KEYCODE_DPAD_RIGHT", Toast.LENGTH_LONG)
                                .show()
                        }

                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            if(rowPos<binding.programsList.size){

                            }else{
                               return@OnRowKeyListener true
                            }
                            Toast.makeText(context, "HorizontalAdapter ["+rowPos+"]["+itemPos+"] KEYCODE_DPAD_RIGHT", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    return@OnRowKeyListener false
                }


            }
            adapter = ProgramAdapter(programs,title,channel,onRowKeyListener)

        }
    }

    fun getFocusRecycleItem(){

    }
}