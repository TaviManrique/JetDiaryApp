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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override fun getAllDiaries(): Flow<RequestState<Diaries>> = if (user != null) {
        try {
            realm.query<Diary>(query = "owner_id == $0", user.id)
                .sort(property = "date", sortOrder = Sort.DESCENDING)
                .asFlow()
                .map { result ->
                    RequestState.Success(
                        data = result.list.groupBy { diary ->
                            diary.date
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                    )
                }
        } catch (e: Exception) {
            flow { emit(RequestState.Error(e)) }
        }
    } else {
        flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
    }


    override fun getDiary(diaryId: ObjectId): RequestState<Diary> = if (user != null) {
        try {
            val diary = realm.query<Diary>(query = "owner_id == $0", diaryId).find().first()
            RequestState.Success(data = diary)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    } else {
        RequestState.Error(UserNotAuthenticatedException())
    }

    private class UserNotAuthenticatedException: Exception("User is not logged in.")
}