package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.databinding.ItemChannelBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel

class ChannelAdapter (private val channels: List<Channel>, private val onChannelItemClickListener:OnChannelItemClickListener) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    var context: Context? = null
    private var channelPos = -1
    fun interface OnChannelItemClickListener {
        fun onChannelItemClick(channel: Channel)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position],position)
        holder.itemView.tag=position
        holder.itemView.setOnKeyListener { view, i, keyEvent -> {

            }
            false
        }
    }

    override fun getItemCount(): Int = channels.size

    inner class ChannelViewHolder(private val binding: ItemChannelBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(channel: Channel, position: Int) {
            binding.channelName.text = channel.name
            val image = context?.getDrawable(channel.icon)
            binding.icon.setImageDrawable(image)
            binding.root.setOnClickListener {
                // 點擊時放大效果
                val anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_scale_big)
                binding.root.startAnimation(anim)
                channelPos=position
                // 此處處理點擊事件
                if(onChannelItemClickListener!=null)
                    onChannelItemClickListener.onChannelItemClick(channel)
            }
            binding.root.setOnFocusChangeListener{view ,hasFocus->
                if(hasFocus){
                    channelPos=position
                    if(onChannelItemClickListener!=null)
                        onChannelItemClickListener.onChannelItemClick(channel)
                }
            }
        }
    }
}