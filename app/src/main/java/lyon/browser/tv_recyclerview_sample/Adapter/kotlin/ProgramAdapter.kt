package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.Fragment.kotlin.ProgramsFragment
import lyon.browser.tv_recyclerview_sample.databinding.ItemProgramBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Channel
import lyon.browser.tv_recyclerview_sample.modelObject.Program

class ProgramAdapter (
    private val programs: List<Program>,
    private val channelName:String,
    private val channel: Channel,
    val onRowKeyListener: HorizontalAdapter.OnRowKeyListener
) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {
    var context: Context? =null
    private val layoutManagers: MutableMap<Int, LinearLayoutManager> = mutableMapOf()
    private val RowItemPos: MutableMap<Int, Int> = mutableMapOf()

    fun getRowItemPos(rowPos:Int):Int?{
        val pos = RowItemPos.get(rowPos)
        return pos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ItemProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bind(programs[position],position)
    }

    override fun getItemCount(): Int = programs.size

    inner class ProgramViewHolder(private val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(program: Program, rowPos: Int) {
            binding.programName.text = program.name
            binding.horizontalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                layoutManagers.put(rowPos, layoutManager as LinearLayoutManager)
                RowItemPos.put(rowPos,0)

                val setRowPosItem = object : HorizontalAdapter.SetRowPosItem {
                    override fun setPos(rowPos: Int, itemPos: Int) {
                        RowItemPos.put(rowPos,itemPos)
                    }
                }
                adapter=HorizontalAdapter(program,channelName , channel, rowPos,onRowKeyListener,setRowPosItem)
            }
        }
    }
}