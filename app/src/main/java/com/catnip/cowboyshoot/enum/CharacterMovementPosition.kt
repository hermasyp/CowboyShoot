package com.catnip.cowboyshoot.enum

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
enum class CharacterMovementPosition(val value: Int) {
    TOP(1),
    MIDDLE(0),
    BOTTOM(-1);
    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
