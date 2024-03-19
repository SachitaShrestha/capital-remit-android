package com.webtecsolutions.capitalremit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.webtecsolutions.capitalremit.data.constants.Config
import com.webtecsolutions.capitalremit.data.dao.TransactionDao
import com.webtecsolutions.capitalremit.data.model.Transaction

@Database(
    entities = [Transaction::class],
    version = Config.DATABASE_VERSION
)
abstract class LocalDatabase : RoomDatabase() {


    abstract fun getTransactionDao(): TransactionDao

}