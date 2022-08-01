package com.example.finallydevice.ScreenStatics.screenbar

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.finallydevice.ScreenStatics.screenbar.dataclass.ScreenStatics
import com.example.finallydevice.ScreenStatics.screenbar.function.DrawBarDouble
import com.example.finallydevice.databinding.FragmentScreenstaticsBinding
import com.example.finallydevice.share.Factory.MyviewModelFactory
import com.example.finallydevice.share.TypeConverter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenstaticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScreenstaticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentScreenstaticsBinding
    private val argument by navArgs<ScreenstaticsFragmentArgs>()
    private lateinit var viewModel: ScreenstaticsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this,MyviewModelFactory(requireActivity().application,argument.type)).get(ScreenstaticsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentScreenstaticsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        val draw= DrawBarDouble(requireContext())
        viewModel.screenstaticslist?.observe(viewLifecycleOwner){
            Log.d("test",it.toString()+argument.type)
            val list:MutableList<ScreenStatics> = mutableListOf<ScreenStatics>()
            var sum:Long=0L
            it.forEach {
                when(argument.type)
                {
                    "usingtimeavg"->{
                        list.add(ScreenStatics(it.usingtimeavg,it.date))
                        sum+=it.usingtimeavg
                    }
                    "usingtimemax"->{
                        list.add(ScreenStatics(it.usingtimemax,it.date))
                        sum+=it.usingtimemax
                    }
                    "offtime"->{
                        list.add(ScreenStatics(it.offtime,it.date))
                        sum+=it.offtime
                    }
                    "count"->{
                        list.add(ScreenStatics(it.count,it.date))
                        sum+=it.count
                    }
                    "usingtime"->{
                        list.add(ScreenStatics(it.usingtime,it.date))
                        sum+=it.usingtime
                    }
                }
            }
            when(argument.type)
            {
                "usingtimeavg"->
                {
                    sum+=viewModel.getstatics_today("usingtimeavg")
                    binding.dayavg.text=TypeConverter.timetotext(sum/(list.size+1))
                    binding.weeksumS.isVisible=false
                    binding.weeksum.isVisible=false
                    binding.typeS.text="平均使用时长"
                    binding.typeS.setTextColor(Color.BLACK)
                }
                "usingtimemax"->
                {
                    sum+=viewModel.getstatics_today("usingtimemax")
                    binding.dayavg.text=TypeConverter.timetotext(sum/(list.size+1))
                    binding.weeksumS.isVisible=false
                    binding.weeksum.isVisible=false
                    binding.typeS.text="连续使用时长"
                    binding.typeS.setTextColor(Color.BLACK)
                }
                "offtime"->{
                    sum+=viewModel.getstatics_today("offtime")
                    binding.dayavg.text=TypeConverter.timetotext(sum/(list.size+1))
                    binding.weeksumS.isVisible=false
                    binding.weeksum.isVisible=false
                    binding.typeS.text="最长锁屏时长"
                    binding.typeS.setTextColor(Color.BLACK)
                }
                "count"->{
                    sum+=viewModel.getstatics_today("count")
                    binding.dayavg.text= String.format("%d",sum/(list.size+1))+"次"
                    binding.weeksumS.isVisible=true
                    binding.weeksum.isVisible=true
                    binding.weeksum.text="${sum}次"
                    binding.typeS.text="拿起次数"
                    binding.typeS.setTextColor(Color.BLACK)
                }
                "usingtime"->{
                    sum+=viewModel.getstatics_today("usingtime")
                    binding.dayavg.text=TypeConverter.timetotext(sum/(list.size+1))
                    binding.weeksumS.isVisible=false
                    binding.weeksum.isVisible=false
                    binding.typeS.text="屏幕使用时长"
                    binding.typeS.setTextColor(Color.BLACK)
                }
            }
            draw.setdata(list,argument.type)
            draw.draw(binding.bar)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScreenstaticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScreenstaticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}