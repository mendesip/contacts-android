package com.example.data.phone

import androidx.room.TypeConverter

class PhoneTypeConverter {
    @TypeConverter
    fun phoneTypeToInt(phone: PhoneType): Int {
        return phone.type
    }

    @TypeConverter
    fun intToPhoneType(type: Int): PhoneType {
        return PhoneType.values().find { it.type == type } ?: PhoneType.MOBILE
    }
}