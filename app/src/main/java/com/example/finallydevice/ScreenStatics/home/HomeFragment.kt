package com.example.finallydevice.ScreenStatics.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finallydevice.databinding.FragmentHomeBinding
import com.example.finallydevice.ScreenStatics.home.recycleview.Item_decoration
import com.example.finallydevice.ScreenStatics.home.recycleview.Screenusingadapter
import com.example.finallydevice.ScreenStatics.home.recycleview.Skiptofragment
import com.example.finallydevice.share.Factory.MyviewModelFactory

class HomeFragment : Fragment(), Skiptofragment {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =
            ViewModelProvider(
                requireActivity(),
                MyviewModelFactory(requireActivity().application)
            ).get(HomeViewModel::class.java)
        lifecycle.addObserver(Lifecycleobsever(homeViewModel))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.time.observe(viewLifecycleOwner) {
            binding.usingtime.text = it
        }
        binding.recyclerview.addItemDecoration(Item_decoration(2,50,false))
        binding.recyclerview.layoutManager= GridLayoutManager(requireActivity(),2)
        val adapter= Screenusingadapter(this)
        binding.recyclerview.adapter=adapter
        homeViewModel.time_statistics.observe(viewLifecycleOwner){
            adapter.setdata(it)
        }
        binding.chip4.setOnClickListener {
            next("usingtime")
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun next(type:String) {
        val action=HomeFragmentDirections.actionNavHomeToScreenstaticsFragment(type)
        findNavController().navigate(action)
    }


}
