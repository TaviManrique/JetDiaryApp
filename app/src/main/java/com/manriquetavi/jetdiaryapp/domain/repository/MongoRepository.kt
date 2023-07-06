package com.manriquetavi.jetdiaryapp.domain.repository

import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

typealias Diaries = Map<LocalDate, List<Diary>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<RequestState<Diaries>>
    fun getDiary(diaryId: ObjectId): Flow<RequestState<Diary>>
    suspend fun addDiary(diary: Diary): RequestState<Diary>
}