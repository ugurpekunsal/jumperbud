package com.jumperbud.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jumperbud.databinding.RecyclerViewTrainingBinding
import com.jumperbud.models.TrainingSession

class TrainingAdapter: RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {

    var trainingSessions = mutableListOf<TrainingSession>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return trainingSessions.size
    }

    inner class ViewHolder(val binding: RecyclerViewTrainingBinding): RecyclerView.ViewHolder(binding.root) {

    }

}