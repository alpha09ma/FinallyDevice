package com.example.finallydevice.share

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finallydevice.R
import com.example.finallydevice.databinding.UpdateFragmentBinding
import com.example.finallydevice.share.Factory.MyviewModelFactory
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData

class UpdateFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateFragment()
    }
    private var _binding:UpdateFragmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var sviewmodel: ShareViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        sviewmodel=ViewModelProvider(requireActivity(),
            MyviewModelFactory(requireActivity().application)
        ).get(ShareViewModel::class.java)
        _binding= UpdateFragmentBinding.inflate(inflater,container,false)
        binding.descreptionChange.setText(args.data.description)
        binding.timeChange.setText(args.data.time)
        binding.typeChange.setText(args.data.type)
        when(args.data.degree)
        {
            4->{binding.importantChange.isChecked=true}
            3->{binding.normalChange.isChecked=true}
            2->{binding.sometiemsChange.isChecked=true}
            1->{binding.noidicateChange.isChecked=true}
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        Log.d("test","发生了什么")
        inflater.inflate(R.menu.add_item,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var degree=0
        when(binding.radioGroup2.checkedRadioButtonId)
        {
            R.id.important_change->degree=4
            R.id.normal_change->degree=3
            R.id.sometiems_change->degree=2
            R.id.noidicate_change->degree=1
        }
        when(item.itemId)
        {
            R.id.save->{
            val newdata= ToDoListData(args.data.id,binding.typeChange.text.toString(),binding.descreptionChange.text.toString(),binding.timeChange.text.toString(),degree)
                sviewmodel.update(newdata)
                findNavController().navigate(R.id.todolistfragment)
        }
        }
        return super.onOptionsItemSelected(item)
    }
}