package com.example.data.phone

import androidx.room.*

@Entity(tableName = "phone", indices = [Index(value = ["number"], unique = true)])
data class Phone(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(defaultValue = "0")
    var contactId: Long = 0,
    @ColumnInfo(defaultValue = "")
    var number: String = "",
    @ColumnInfo(defaultValue = "0")
    var type: PhoneType = PhoneType.MOBILE
)