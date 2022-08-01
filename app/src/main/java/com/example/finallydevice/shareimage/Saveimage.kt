package com.example.finallydevice.shareimage

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider


class Saveimage{
    companion object{
     fun saveImage(bitmap: Bitmap,activity:Activity):Uri {
            val fileName = "shareImage.jpg"
            activity.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.flush()
                it.close()
            }
         val file=activity.getFileStreamPath(fileName)

         return FileProvider.getUriForFile(activity.applicationContext,activity.packageName+".fileprovider",file)
        }

    }
}
