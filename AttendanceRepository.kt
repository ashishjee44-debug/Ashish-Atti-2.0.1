package com.example.ashishatte.data

import com.example.ashishatte.model.Attendance
import com.example.ashishatte.model.Employee
import com.example.ashishatte.model.OfficeLocation
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AttendanceRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend fun currentEmployee(): Employee? {
        val uid = auth.currentUser?.uid ?: return null
        return db.collection("employees").document(uid).get().await()
            .toObject(Employee::class.java)
    }

    suspend fun office(): OfficeLocation? =
        db.collection("settings").document("office").get().await()
            .toObject(OfficeLocation::class.java)

    suspend fun saveAttendance(
        employee: Employee,
        type: String,
        latitude: Double,
        longitude: Double,
        distanceMeters: Double,
        evidencePath: String? = null
    ) {
        val ref = db.collection("attendance").document()
        val record = hashMapOf(
            "id" to ref.id,
            "employeeUid" to employee.uid,
            "employeeName" to employee.name,
            "type" to type,
            "clientCreatedAtMillis" to System.currentTimeMillis(),
            "serverCreatedAt" to FieldValue.serverTimestamp(),
            "latitude" to latitude,
            "longitude" to longitude,
            "distanceMeters" to distanceMeters,
            "selfieEvidencePath" to evidencePath
        )
        ref.set(record).await()
    }

    suspend fun setOffice(location: OfficeLocation) {
        db.collection("settings").document("office").set(location).await()
    }
}
