package com.application.shoprye.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.shoprye.models.RyeJob
import kotlinx.coroutines.flow.Flow

@Dao
interface RyeJobDao {
    @Query("SELECT * FROM rye_jobs ORDER BY name")
    fun getAllRyeJobs(): Flow<List<RyeJob>>

    @Query("SELECT * FROM rye_jobs WHERE id = :ryeJobId")
    fun getRyeJob(ryeJobId: Int): Flow<RyeJob>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ryeJobs: List<RyeJob>)
}