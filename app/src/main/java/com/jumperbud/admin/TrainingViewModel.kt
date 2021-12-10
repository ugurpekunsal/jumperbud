package com.jumperbud.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.jumperbud.models.NODE_TRAINING_SESSIONS
import com.jumperbud.models.TrainingSession

class TrainingViewModel: ViewModel() {
    private val dbTrainingSessions = FirebaseDatabase.getInstance()
        .getReference(NODE_TRAINING_SESSIONS)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _trainingSession = MutableLiveData<TrainingSession>()
    val trainingSession: LiveData<TrainingSession> get() = _trainingSession

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

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val trainingSession = snapshot.getValue(TrainingSession::class.java)
            trainingSession?.id = snapshot.key
            _trainingSession.value = trainingSession!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    fun getRealtimeUpdate() {
        dbTrainingSessions.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbTrainingSessions.removeEventListener(childEventListener)
    }
}