package com.jumperbud.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jumperbud.R
import com.jumperbud.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    private val adapter = TrainingAdapter()

    private lateinit var viewModel: TrainingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(TrainingViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSessions.adapter = adapter

        binding.addButton.setOnClickListener {
            AddTrainingDialogFragment().show(childFragmentManager, "")
        }

        viewModel.trainingSession.observe(viewLifecycleOwner, Observer {
            adapter.addTrainingSession(it)
        })

        viewModel.getRealtimeUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}