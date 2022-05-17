package com.example.testvrg.screen


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testvrg.R
import kotlinx.android.synthetic.main.screan_fragment.*

class ScreenFragment : Fragment() {

    private lateinit var screenViewModel: ScreenFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        screenViewModel = ViewModelProvider(this)[ScreenFragmentViewModel::class.java]
        return inflater.inflate(R.layout.screan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val adapter = ScreenAdapter()

        rcView.adapter = adapter

        adapter.listener = { after ->
            Log.d("MyLog", "onViewCreated: after = $after")
            screenViewModel.getDataInfo(after)
        }

        screenViewModel.getDataInfo(after = null)
        screenViewModel.allApi.observe(viewLifecycleOwner) { response ->
            adapter.setList(response.data.children, response.data.after)
        }


    }

}

