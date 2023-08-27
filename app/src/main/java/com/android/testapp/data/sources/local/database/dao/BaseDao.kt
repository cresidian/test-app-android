package com.android.testapp.data.sources.local.database.dao

import androidx.room.*

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rows: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(row: T): Long

    @Update
    suspend fun update(row: T): Int

    @Delete
    suspend fun delete(row: T): Int

}