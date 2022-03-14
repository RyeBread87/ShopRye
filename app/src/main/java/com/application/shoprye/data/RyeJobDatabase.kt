package com.application.shoprye.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.shoprye.dao.RyeJobDao
import com.application.shoprye.dao.ShoppingCartDao
import com.application.shoprye.models.RyeJob
import com.application.shoprye.models.ShoppingCartEntry

@Database(entities = [ShoppingCartEntry::class, RyeJob::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RyeJobDatabase : RoomDatabase() {
    abstract fun shoppingCartDao (): ShoppingCartDao
    abstract fun ryeJobDao(): RyeJobDao

    companion object {
        @Volatile private var INSTANCE: RyeJobDatabase? = null

        fun getInstance(context: Context): RyeJobDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, RyeJobDatabase::class.java, "shop_rye_db").build().also { INSTANCE = it }
            }
        }
    }
}
