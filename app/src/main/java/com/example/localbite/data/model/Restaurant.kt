package com.example.localbite.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE)])
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(index = true)
    val userId: Long,
    val name: String,
    val address: String,
    val description: String,
    val image: String,
)
