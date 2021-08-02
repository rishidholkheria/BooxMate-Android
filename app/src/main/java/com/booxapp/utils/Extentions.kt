package com.booxapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

