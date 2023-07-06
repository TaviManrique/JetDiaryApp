package com.manriquetavi.jetdiaryapp.domain.repository

import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.util.Constants.APP_ID
import com.manriquetavi.jetdiaryapp.util.RequestState
import com.manriquetavi.jetdiaryapp.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.*
import org.mongodb.kbson.ObjectId
import java.time.ZoneId

object MongoDB: MongoRepository {

    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration
                .Builder(user, setOf(Diary::class))
                .initialSubscriptions {sub ->
                    add(
                        query = sub.query<Diary>("owner_id == $0", user.id),
                        name = "User's Diaries"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllDiaries(): Flow<RequestState<Diaries>> = flow {
        if (user != null) {
            try {
                realm.query<Diary>(query = "owner_id == $0", user.id)
                    .sort(property = "date", sortOrder = Sort.DESCENDING).asFlow().collect {
                        emit(
                            RequestState.Success(
                                data = it.list.groupBy { diary ->
                                    diary.date
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                }
                            )
                        )
                    }
            } catch (e: Exception) {
                emit(RequestState.Error(e))
            }
        } else {
            emit(RequestState.Error(UserNotAuthenticatedException()))
        }
    }



    override fun getDiary(diaryId: ObjectId): Flow<RequestState<Diary>> = flow {
        if (user != null) {
            try {
                val diary = realm.query<Diary>(query = "_id == $0", diaryId).find().first()
                emit(RequestState.Success(data = diary))
            } catch (e: Exception) {
                emit(RequestState.Error(e))
            }
        } else {
            emit(RequestState.Error(UserNotAuthenticatedException()))
        }
    }

    override suspend fun addDiary(diary: Diary): RequestState<Diary> =
        if (user != null) {
            realm.write {
                try {
                    val newDiary = copyToRealm(diary.apply { owner_id = user.id })
                    RequestState.Success(data = newDiary)
                } catch (e: Exception) {
                    RequestState.Error(e)
                }
            }
        } else {
            RequestState.Error(UserNotAuthenticatedException())
        }


    private class UserNotAuthenticatedException: Exception("User is not logged in.")
}