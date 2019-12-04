package com.andreykapustindev.passwordwallet.model

object SettingRepository {

    private var isItemChecked: BooleanArray = booleanArrayOf(true, true, true, true)
    private var numbers: Int = 10

    fun setIsItemChecked(isItemChecked: BooleanArray) {
        this.isItemChecked = isItemChecked
    }

    fun setNumbers(numbers: Int) {
        this.numbers = numbers
    }

    fun getIsItemChecked() = isItemChecked

    fun getNumbers() = numbers
}