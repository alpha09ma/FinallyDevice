package com.example.finallydevice.share

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.finallydevice.R
import com.example.finallydevice.databinding.FragmentAddItemBinding
import com.example.finallydevice.todolist_shortterm.todolist.dataclass.ToDoListData
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItem : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _bingding:FragmentAddItemBinding?=null
    private var navController:NavController?=null
    private lateinit var _time:String
    private lateinit var _type:String
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val binding get() = _bingding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _bingding= FragmentAddItemBinding.inflate(inflater,container,false)

        binding.customtime.setOnClickListener {
            val edit:EditText= EditText(requireContext())
            edit.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
            edit.hint="请按时间格式输入且时长不得大于3小时"
            val dialog=MaterialAlertDialogBuilder(requireContext())
                .setTitle("请输入专注时长")
                .setView(edit)
                .setNegativeButton("取消"){
                        _,_->
                }
                .setPositiveButton("确认"){
                        _,_->val time=edit.text.toString()
                    if(time.substring(2,3)!=":" || time.substring(5,6)!=":")
                        Toast.makeText(context,"请按时间格式输入",Toast.LENGTH_LONG).show()
                    else
                    {
                        var legal=true
                        val timelist=time.split(":")
                        timelist.forEach {
                            val c=it.toCharArray()
                            c.forEach {
                                if(it>'9'||it<'0')
                                {
                                    Toast.makeText(context,"请按时间格式输入",Toast.LENGTH_LONG).show()
                                    legal=false
                                }
                            }
                        }
                        if(legal) {
                            var sum = timelist[1][0].digitToInt() * 10 + timelist[1][1].digitToInt()
                            if (sum > 59)
                                Toast.makeText(context, "分钟输入错误", Toast.LENGTH_LONG).show()
                            else {
                                sum = timelist[2][0].digitToInt() * 10 + timelist[2][1].digitToInt()
                                if (sum > 59)
                                    Toast.makeText(context, "秒数输入错误", Toast.LENGTH_LONG).show()
                                else {
                                    sum =
                                        timelist[0][0].digitToInt() * 10 + timelist[0][1].digitToInt()
                                    if (sum > 3)
                                        Toast.makeText(context, "专注时长必须小于3小时", Toast.LENGTH_LONG)
                                            .show()
                                    else {
                                        if (sum == 3) {
                                            if (timelist[1] != "00")
                                                Toast.makeText(
                                                    context,
                                                    "专注时长必须小于3小时",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            else
                                                if (timelist[2] != "00")
                                                    Toast.makeText(
                                                        context,
                                                        "专注时长必须小于3小时",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                else
                                                    _time = time
                                        } else
                                            _time = time
                                    }
                                }
                            }
                        }
                    }
                }
                .create()
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
        }
        binding.customtype.setOnClickListener {
            val edit:EditText= EditText(requireContext())
            val dialog=MaterialAlertDialogBuilder(requireContext())
                .setTitle("请输入事务类型")
                .setView(edit)
                .setNegativeButton("取消"){
                        _,_->
                }
                .setPositiveButton("确认"){
                        _,_->_type=edit.text.toString()
                }
                .create()
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
        navController=findNavController()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_item,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.save->{
                val legal=sendmessage()
                if(legal)
                    navController!!.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun sendmessage():Boolean{
        var legal=true
        val description:String=binding.input.text.toString()
        var time:String="00:25:00"
        var type:String="默认"
        var degree:Int=0
        when(binding.setcolock.checkedRadioButtonId)
        {
            binding.minute25.id->time="00:25:00"
            binding.minute45.id->time="00:45:00"
            binding.hour1.id->time="01:00:00"
            binding.customtime.id->{
                if(this::_time.isInitialized)
                    time=_time
                else
                {
                    Toast.makeText(context,"请选择或输入合法专注时长",Toast.LENGTH_LONG).show()
                    legal=false
                }
            }
        }
        Log.d("????",binding.settype.checkedRadioButtonId.toString())
        when(binding.settype.checkedRadioButtonId)
        {
            binding.learn.id-> type="学习"
            binding.work.id->type="工作"
            binding.read.id->type="阅读"
            binding.customtype.id->{
                if(this::_type.isInitialized)
                    type=_type
                else
                {
                    Toast.makeText(context,"请选择或输入事务类型",Toast.LENGTH_LONG).show()
                    legal=false
                }
            }
        }
        when(binding.setdegree.checkedRadioButtonId)
        {
            binding.important.id->degree=4
            binding.normal.id->degree=3
            binding.sometimes.id->degree=2
            binding.notindicate.id->degree=1
        }
        if(legal)
        {
            val viewmodel: ShareViewModel =ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
            viewmodel.add(ToDoListData(0,type,description,time, degree))
        }
        return legal
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddItem.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddItem().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}