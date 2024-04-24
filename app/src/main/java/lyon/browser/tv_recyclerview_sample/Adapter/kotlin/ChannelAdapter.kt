package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.R
import lyon.browser.tv_recyclerview_sample.databinding.ItemChannelBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel

class ChannelAdapter (private val channels: List<Channel>,
                      private val onChannelItemClickListener:OnChannelItemClickListener,
                        private val onChannelExpanded: OnChannelExpanded,
                      private val onChannelCollapse: OnChannelCollapse
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    var context: Context? = null
    private var channelPos = -1
    private var focusView: View?=null

    fun interface OnChannelItemClickListener {
        fun onChannelItemClick(channel: Channel, position: Int)
    }

    fun interface OnChannelExpanded {
        fun expanded(channelPos:Int)
    }

    fun interface OnChannelCollapse{
        fun collapse()
    }

    fun getFocusView():View?{
        return focusView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position],position)
        holder.itemView.tag=position
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
                    onChannelItemClickListener.onChannelItemClick(channel,position)
            }
            binding.root.setOnFocusChangeListener{view ,hasFocus->
                if(hasFocus){
                    focusView = view
                    channelPos=position
                    onChannelExpanded.expanded(channelPos)
                    if(onChannelItemClickListener!=null)
                        onChannelItemClickListener.onChannelItemClick(channel,position)
                }
            }

            binding.root.setOnKeyListener { view, keyCode, keyEvent -> run{
                if(keyEvent.action==KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_LEFT -> {

                            Toast.makeText(
                                context,
                                "ChannelAdapter[" + position + "] KEYCODE_DPAD_LEFT",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            return@setOnKeyListener true
                        }

                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            onChannelCollapse.collapse()
                            Toast.makeText(
                                context,
                                "ChannelAdapter [" + position + "] KEYCODE_DPAD_RIGHT",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            return@setOnKeyListener true
                        }

                        KeyEvent.KEYCODE_DPAD_UP -> {
                            Toast.makeText(
                                context,
                                "ChannelAdapter [" + position + "] KEYCODE_DPAD_UP",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            Toast.makeText(
                                context,
                                "ChannelAdapter [" + position + "] KEYCODE_DPAD_DOWN",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
                false
            }
        }
    }


}