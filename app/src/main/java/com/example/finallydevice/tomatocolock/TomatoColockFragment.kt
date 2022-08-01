package com.example.finallydevice.tomatocolock

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finallydevice.R
import com.example.finallydevice.databinding.TomatoColockFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TomatoColockFragment: Fragment() {
    private lateinit var _binding:TomatoColockFragmentBinding
    private val binding get() = _binding!!
    private val argument by navArgs<TomatoColockFragmentArgs>()
    private lateinit var viewModel: TomatoColockViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TomatoColockViewModel::class.java)
        _binding= TomatoColockFragmentBinding.inflate(inflater,container,false)
        viewModel.time.observe(viewLifecycleOwner){
            binding.timecolock.text=it
        }
        viewModel.setTime(argument.timeoclock)
        var play_stop:Boolean=true
        binding.playstop.setOnClickListener{
            if(play_stop)
            {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("是否暂停专注计时")
                    .setPositiveButton("是"){
                            dialog,which->viewModel.timer.cancel()
                        binding.playstop.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                    .setNegativeButton("否"){
                            _,_->
                    }
                    .create()
                    .show()
                play_stop=false
            }
            else
            {
                binding.playstop.setImageResource(R.drawable.ic_baseline_pause_24)
                viewModel.timer.begin()
                play_stop=true
            }
        }
        return binding.root
    }

}