package com.jumperbud.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.jumperbud.models.NODE_TRAINING_SESSIONS
import com.jumperbud.models.TrainingSession

class TrainingViewModel: ViewModel() {
    private val dbTrainingSessions = FirebaseDatabase.getInstance()
        .getReference(NODE_TRAINING_SESSIONS)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    fun addTrainingSession(trainingSession: TrainingSession) {
        trainingSession.id = dbTrainingSessions.push().key

        dbTrainingSessions.child(trainingSession.id!!).setValue(trainingSession).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = null
            }
            else {
                _result.value = it.exception
            }
        }
    }
}