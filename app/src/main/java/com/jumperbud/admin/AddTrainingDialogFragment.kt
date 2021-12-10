package com.jumperbud.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jumperbud.R
import com.jumperbud.databinding.FragmentAddTrainingDialogBinding
import com.jumperbud.models.TrainingSession
import java.sql.Time
import java.text.SimpleDateFormat

class AddTrainingDialogFragment : DialogFragment() {

    private var _binding: FragmentAddTrainingDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrainingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTrainingDialogBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(TrainingViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.added_training_session)
            }
            else {
                getString(R.string.error, it.message)
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })
/*
    var id: String? = null,
    var fullName: String? = null,
    var trainerName: String? = null,
    var dateTime: LocalDateTime? = null
 */
        binding.buttonAdd.setOnClickListener {
            val fullName = binding.editTextFullName.text.toString().trim()
            val trainerName = binding.editTextTrainerName.text.toString().trim()
            val date = binding.editTextDate.text.toString().trim()
            val time = binding.editTextTime.text.toString().trim()

            if (fullName.isEmpty()) {
                binding.editTextFullName.error = "This field is required!"
                return@setOnClickListener
            }

            if (trainerName.isEmpty()) {
                binding.editTextTrainerName.error = "This field is required!"
                return@setOnClickListener
            }

            if (date == null) {
                binding.editTextDate.error = "This field is required!"
                return@setOnClickListener
            }

            if (time == null) {
                binding.editTextDate.error = "This field is required!"
                return@setOnClickListener
            }

            val trainingSession = TrainingSession()
            trainingSession.fullName = fullName
            trainingSession.trainerName = trainerName
            trainingSession.date = date;
            trainingSession.time = time;

            viewModel.addTrainingSession(trainingSession)
        }
    }
}