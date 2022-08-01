package com.example.finallydevice.AppusingStatics.appusinglist.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "appusing_day")
data class Appusingdata_day_database (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val packagename:String,
    val name:String,
    val totaltime:Long,
    val date:LocalDate
    )
