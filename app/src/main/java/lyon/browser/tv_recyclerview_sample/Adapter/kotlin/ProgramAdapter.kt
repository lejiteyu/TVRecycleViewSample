package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        fun bind(program: Program, position: Int) {
            binding.programName.text = program.name
            binding.horizontalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                layoutManagers.put(position, layoutManager as LinearLayoutManager)
                adapter=HorizontalAdapter(program,channelName , channel, position,onRowKeyListener)
            }

        }
    }
}