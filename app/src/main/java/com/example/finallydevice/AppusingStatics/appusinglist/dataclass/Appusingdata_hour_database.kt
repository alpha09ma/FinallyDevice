package com.example.finallydevice.AppusingStatics.appusinglist.dataclass

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore
import java.io.Serializable

@Entity(tableName = "appusing_hour")
data class Appusingdata_hour_database(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @Ignore var icon:Drawable?,
    var packagename:String,
    var name:String,
    var totaltime:Long,
    var starttime:Int,
): Serializable{
    constructor():this(0,null,"","",0L,0)
}
