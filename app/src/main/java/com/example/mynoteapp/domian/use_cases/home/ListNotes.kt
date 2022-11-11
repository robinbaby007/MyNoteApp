package com.example.mynoteapp.domian.use_cases.home

import com.example.mynoteapp.domian.HomeRepository
import com.example.mynoteapp.utils.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListNotes @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Flow<Response<List<DocumentSnapshot>>> {
        return homeRepository.listNotes()
    }

}