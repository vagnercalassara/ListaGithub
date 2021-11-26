package com.vagner.github.model

data class Github(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int,
)