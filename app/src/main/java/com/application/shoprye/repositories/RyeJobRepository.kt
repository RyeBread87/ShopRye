package com.application.shoprye.repositories

import com.application.shoprye.RetrofitService
import com.application.shoprye.dao.RyeJobDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RyeJobRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val ryeJobDao: RyeJobDao
    ) {

    suspend fun getAllRyeJobs() = retrofitService.getAllRyeJobs()

    suspend fun insertAllRyeJobs() {
        val ryeJobs = retrofitService.getAllRyeJobs()
        ryeJobDao.insertAll(ryeJobs.body()!!)
    }

    fun getRyeJob(ryeJobId: Int) = ryeJobDao.getRyeJob(ryeJobId)
}