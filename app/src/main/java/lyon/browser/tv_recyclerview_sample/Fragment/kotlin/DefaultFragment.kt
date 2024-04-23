package lyon.browser.tv_recyclerview_sample.Fragment.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lyon.browser.tv_recyclerview_sample.databinding.FragmentDefaultBinding

class DefaultFragment : Fragment() {

    private lateinit var binding: FragmentDefaultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultBinding.inflate(inflater, container, false)
        binding.title.text="推薦"
        return binding.root
    }
}