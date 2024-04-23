package lyon.browser.tv_recyclerview_sample.Fragment.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import lyon.browser.tv_recyclerview_sample.Adapter.kotlin.ProgramAdapter
import lyon.browser.tv_recyclerview_sample.databinding.FragmentProgramsBinding
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
        }
    }

    private fun updateProgramsList(channel: Channel) {
        // 根據 channel 的信息更新界面，包括圖示和文字
        binding.title.text = channel.name
        binding.icon.setImageDrawable(activity?.resources?.getDrawable(channel.icon))
    }

    private fun setupProgramsList(programs: List<Program>) {
        binding.programsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = ProgramAdapter(programs)
        }
    }
}