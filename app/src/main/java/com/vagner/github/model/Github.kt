package com.vagner.github.model

import com.google.gson.annotations.SerializedName

data class Github(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)