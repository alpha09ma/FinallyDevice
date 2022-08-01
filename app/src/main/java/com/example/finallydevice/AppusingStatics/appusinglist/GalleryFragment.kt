package com.example.finallydevice.AppusingStatics.appusinglist

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finallydevice.databinding.FragmentGalleryBinding
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.AppusingStatics.appusinglist.recycleview.AppusingStaticsApdater
import com.example.finallydevice.AppusingStatics.appusinglist.recycleview.toOneAppStatics
import com.example.finallydevice.share.Factory.MyviewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class GalleryFragment : Fragment(),toOneAppStatics {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this,MyviewModelFactory(requireActivity().application)).get(
                GalleryViewModel::class.java)
        viewModel.appusinghourdatabase.observe(viewLifecycleOwner){
            if(viewModel.appusinghourdatabase.value.isNullOrEmpty())
                for(i in 1..LocalTime.now().hour)
                    viewModel.addappusinghour(
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)),
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(i,0,0)),i-1)
        }
        viewModel.appusingdaydatabase.observe(viewLifecycleOwner){

            if(viewModel.appusingdaydatabase.value.isNullOrEmpty())
                for(i in 1..LocalDate.now().dayOfWeek.value-1)
                {
                    Log.d("...",i.toString())
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.addappusingday(LocalDateTime.of(LocalDate.now().plusDays((0-i).toLong()), LocalTime.of(0,0,0)),
                            LocalDateTime.of(LocalDate.now().plusDays((0-i+1).toLong()), LocalTime.of(0,0,0)))
                    }
                }

        }
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        val apdater=AppusingStaticsApdater(this)
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerview.adapter=apdater
        //val textView: TextView = binding.textGallery
        viewModel.getappusinglisttoday()
        viewModel.list.observe(viewLifecycleOwner) {
            apdater.setdata(it)
        }

        binding.searchView.setOnQueryTextListener(
            object :SearchView.OnQueryTextListener{
                var list = viewModel.list.value
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onQueryTextSubmit(querytext: String?): Boolean {
                    list = viewModel.list.value
                    list=list?.filter {
                        it.name.contains(querytext!!,false)
                    }
                    viewModel.setdata(list!!)
                    return false
                }
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onQueryTextChange(querytext: String?): Boolean {
                    list = viewModel.list_start
                    list=list?.filter {
                        it.name.contains(querytext!!,false)
                    }
                    viewModel.setdata(list!!)
                    return false
                }
            }
        )
        binding.searchView.setOnCloseListener(
            object :SearchView.OnCloseListener{
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onClose(): Boolean {
                   viewModel.recover()
                    binding.searchView.clearFocus()
                    return true
                }
            }
        )
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onclicked(appinfo:Appusingdata_hour_database) {
        val action=GalleryFragmentDirections.actionNavGalleryToBarchartFragment(appinfo)
        findNavController().navigate(action)
    }
}