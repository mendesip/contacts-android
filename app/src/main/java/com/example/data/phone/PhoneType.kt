package com.example.data.phone

import androidx.annotation.StringRes
import com.example.ui.contact.R

enum class PhoneType(
    val type: Int,
    @StringRes val typeName: Int
) {
    MOBILE(0, R.string.phone_type_mobile),
    COMMERCIAL(1, R.string.phone_type_commercial),
    RESIDENTIAL(2, R.string.phone_type_residential)
}