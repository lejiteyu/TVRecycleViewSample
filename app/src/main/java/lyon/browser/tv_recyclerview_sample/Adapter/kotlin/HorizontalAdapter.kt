package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.text.method.BaseKeyListener
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.databinding.ItemViewBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel
import lyon.browser.tv_recyclerview_sample.modelObject.Program
import lyon.browser.tv_recyclerview_sample.modelView.MainViewModel

class HorizontalAdapter (
    private val program:Program,
    private val channelName:String ,
    private val channel: Channel,
    val rowPos:Int,
    val onRowKeyListener: OnRowKeyListener
) : RecyclerView.Adapter<HorizontalAdapter.ItemHolder>() {
    var context: Context? =null

    fun interface OnRowKeyListener{
        fun onKey(view: View, keyCode:Int, keyEvent:KeyEvent,rowPos:Int,itemPos:Int , size:Int):Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 20

    inner class ItemHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.name.text = program.name+"["+position+"]"
            binding.imageView.setImageDrawable(context?.getDrawable(channel.icon))
            binding.root.background = context?.getDrawable(R.drawable.selector_channel_item)
            binding.root.setOnClickListener {

            }
            binding.root.setOnFocusChangeListener { view, b ->
                if(b){

                }else{

                }

            }
            binding.root.setOnKeyListener { view, keyCode, keyEvent ->
                onRowKeyListener.onKey(view, keyCode, keyEvent,rowPos,position,getItemCount())
            }
        }
    }
}