package com.example.maps.data.local

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

private const val QUALITY_COMPRESS = 100

class Converters {

    @TypeConverter
    fun ByteArray.toBitmap(): Bitmap {
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }

    @TypeConverter
    fun Bitmap.fromBitmap(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, QUALITY_COMPRESS, outputStream)
        return outputStream.toByteArray()
    }
}
