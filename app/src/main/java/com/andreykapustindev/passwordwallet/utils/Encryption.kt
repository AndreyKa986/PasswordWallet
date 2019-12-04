package com.andreykapustindev.passwordwallet.utils

import kotlin.random.Random

fun encode(string: String): String {
    val number = Random.nextInt(10, 100)
    val numberList = "$number".toList()
    val charList = string.toList()
    val list = mutableListOf<Char>()
    for (i in numberList) {
        list.add(i.plus(50))
    }
    for (i in charList) {
        list.add(i.plus(number))
    }
    return list.joinToString("")
}

fun decode(string: String): String {
    val list = string.toList()
    val numberList = mutableListOf<Char>()
    for (i in 0..1) {
        numberList.add(list[i].minus(50))
    }
    val number = numberList.joinToString("").toInt()
    val newList = mutableListOf<Char>()
    for (i in 2 until list.size) {
        newList.add(list[i].minus(number))
    }
    return newList.joinToString("")
}
