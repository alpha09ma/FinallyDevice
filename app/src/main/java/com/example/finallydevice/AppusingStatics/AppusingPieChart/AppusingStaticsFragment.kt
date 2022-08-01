package com.example.finallydevice.AppusingStatics.AppusingPieChart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finallydevice.AppusingStatics.AppusingPieChart.function.Drawpie
import com.example.finallydevice.R
import com.example.finallydevice.databinding.AppusingStaticsFragmentBinding
import com.example.finallydevice.share.Factory.MyviewModelFactory

class AppusingStaticsFragment : Fragment() {

    private var _binding:AppusingStaticsFragmentBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AppusingStaticsFragment()
    }

    private lateinit var viewModel: AppusingStaticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,MyviewModelFactory(requireActivity().application)).get(
            AppusingStaticsViewModel::class.java)
        _binding= AppusingStaticsFragmentBinding.inflate(inflater, container, false)

        val draw: Drawpie = Drawpie(binding.pie)
        viewModel.appusinglist.observe(viewLifecycleOwner){
            draw.setdata(it)
            draw.draw()
        }
        viewModel.setdata()
        binding.moredata.setOnClickListener {
            findNavController().navigate(R.id.nav_gallery)
        }
        //Log.d("???",(binding.pie is PieChart).toString())
        return binding.root
    }

}