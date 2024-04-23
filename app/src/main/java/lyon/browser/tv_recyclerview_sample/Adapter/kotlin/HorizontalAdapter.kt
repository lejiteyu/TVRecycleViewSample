package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.databinding.ItemViewBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel
import lyon.browser.tv_recyclerview_sample.modelObject.Program

class HorizontalAdapter (private val program:Program, private val channelName:String , private val channel: Channel) : RecyclerView.Adapter<HorizontalAdapter.ItemHolder>() {
    var context: Context? =null
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
        }
    }
}