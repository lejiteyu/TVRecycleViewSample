package lyon.browser.tv_recyclerview_sample.Adapter.kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lyon.browser.tv_recyclerview_sample.databinding.ItemProgramBinding
import lyon.browser.tv_recyclerview_sample.modelObject.Program

class ProgramAdapter (private val programs: List<Program>) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ItemProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bind(programs[position])
    }

    override fun getItemCount(): Int = programs.size

    inner class ProgramViewHolder(private val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(program: Program) {
            binding.programName.text = program.name
        }
    }
}