package com.manriquetavi.jetdiaryapp.domain.repository

import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias Diaries = Map<LocalDate, List<Diary>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<RequestState<Diaries>>
}