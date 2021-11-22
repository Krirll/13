package com.krirll.for_max

import androidx.room.*

@Entity
data class LocalModel(
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "date") val date : String
) {
    @PrimaryKey(autoGenerate = true) var id : Int? = null
}

@Dao
interface LocalDao {

    @Insert
    fun insert(list : LocalModel)

    @Query("SELECT * FROM LocalModel")
    fun getAll() : List<LocalModel>

    @Query("DELETE FROM LocalModel")
    fun deleteAll()

}

@Database(entities = [LocalModel::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun localDao() : LocalDao
}