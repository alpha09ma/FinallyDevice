package com.example.finallydevice.AppusingStatics.Appusing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.finallydevice.AppusingStatics.Appusing.function.Drawbar
import com.example.finallydevice.R
import com.example.finallydevice.databinding.BarchartFragmentBinding
import com.example.finallydevice.share.Factory.MyviewModelFactory
import com.example.finallydevice.share.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BarchartFragment : Fragment() {

    private var _binding: BarchartFragmentBinding? = null
    private val binding get() = _binding!!
    private val argument by navArgs<BarchartFragmentArgs>()
    companion object {
        fun newInstance() = BarchartFragment()
    }

    private lateinit var viewModel: BarchartViewModel
    private lateinit var drawbar: Drawbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,MyviewModelFactory(requireActivity().application,argument.appdata)).get(BarchartViewModel::class.java)
        _binding = BarchartFragmentBinding.inflate(inflater,container,false)
        //drawbar.draw(binding.bar,24)
        drawbar= Drawbar(requireContext(),argument.appdata)
        binding.appicon1.setImageDrawable(argument.appdata.icon)
        binding.appname1.setText(argument.appdata.name)
        binding.totaltimetoday.text=TypeConverter.timetotext(argument.appdata.totaltime)
        viewModel.appusinghour.observe(viewLifecycleOwner){
            drawbar.setdata(it,24,argument.appdata.totaltime)
            drawbar.draw(binding.bar,24)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.barfragment,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.today->{ viewModel.appusinghour.observe(viewLifecycleOwner){
                drawbar.setdata(it,24,argument.appdata.totaltime)
                drawbar.draw(binding.bar,24)
            }

            }
            R.id.week->{
                viewModel.appusingday.observe(viewLifecycleOwner){
                drawbar.setdata(it,7,argument.appdata.totaltime)
                drawbar.draw(binding.bar,7)
            }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}