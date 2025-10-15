package com.example.bitfitproj5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Insert
    fun insert(food: FoodEntity)

    @Insert
    fun insertAll(campgrounds: List<FoodEntity>)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}