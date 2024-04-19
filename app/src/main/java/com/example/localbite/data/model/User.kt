package com.example.localbite.data.model

import android.database.Cursor
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters


enum class UserType { RESTAURANT, CUSTOMER }

class UserTypeConverter{
    @TypeConverter
    fun fromUserType(userType: UserType): String {
        return userType.name
    }
    @TypeConverter
    fun toUserType(userType: String): UserType {
        return UserType.valueOf(userType)
    }
}
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    @TypeConverters(UserTypeConverter::class)
    val userType: UserType,
)
