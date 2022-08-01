package com.example.finallydevice.ScreenStatics.home.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "screenusing")
data class Screenusingdata(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val date:LocalDate,
    val usingtime:Long,
    val count:Long,
    val usingtimemax:Long,
    val usingtimeavg:Long,
    val offtime:Long,
)