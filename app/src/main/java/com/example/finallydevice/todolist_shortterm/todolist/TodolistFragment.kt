package com.example.finallydevice.todolist_shortterm.todolist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finallydevice.R
import com.example.finallydevice.databinding.FragmentTodolistBinding
import com.example.finallydevice.share.Factory.MyviewModelFactory
import com.example.finallydevice.todolist_shortterm.todolist.recycleview.SkiptoColock
import com.example.finallydevice.share.ShareViewModel
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData
import com.example.finallydevice.todolist_shortterm.MyAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TodolistFragment : Fragment(), SkiptoColock {

    private var _binding: FragmentTodolistBinding? = null
    private lateinit var navController: NavController
    private lateinit var todolistViewModel: ShareViewModel
    private lateinit var adapter: MyAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todolistViewModel=ViewModelProvider(requireActivity(),MyviewModelFactory(requireActivity().application))
            .get(ShareViewModel::class.java)

        _binding = FragmentTodolistBinding.inflate(inflater, container, false)

        binding.recycleview.layoutManager = LinearLayoutManager(requireActivity())
        adapter= MyAdapter(this)
        navController=findNavController()
        binding.recycleview.adapter=adapter
        binding.add.isVisible=true
        binding.add.setOnClickListener{
            navController.navigate(R.id.addItem)
        }
        todolistViewModel.item.observe(viewLifecycleOwner){
            adapter.setdata(it)
        }
        return binding.root
    }
    override fun onPlayerclicked(time:String) {
        val action=TodolistFragmentDirections.actionTodolistfragmentToTomatoColockFragment(time)
        navController.navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun showdialog(itemdata: ToDoListData, position:Int){
        val array= arrayOf<String>("修改","删除","清空代办")
        val dialog=MaterialAlertDialogBuilder(requireContext())
            .setTitle("请选择以下操作")
            .setItems(array){
                    dialog,which,->
                    when(which)
                    {
                        0->{
                            val action=TodolistFragmentDirections.actionTodolistfragmentToUpdateFragment(itemdata)
                            navController.navigate(action)
                        }
                        1->{
                            todolistViewModel.delete(itemdata)
                            Toast.makeText(requireContext(),"成功删除",Toast.LENGTH_LONG).show()
                        }
                        2->{
                            todolistViewModel.deleteall()
                            Toast.makeText(requireContext(),"成功删除",Toast.LENGTH_LONG).show()
                        }
                    }
            }
            .create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

}