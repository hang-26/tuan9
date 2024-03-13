package com.example.oneexceriseactivity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DateTimeException

/**
 * Xác định dữ liệu bằng thực thể
 */
data class NoteData (
    //Khóa chính được cập nhật tự động
    val id: Int = 0,
    var title: String = " ",
    var content: String = " ",
    var editTime: Long = 0,
    var isChecked: Boolean = false
        )
{
}