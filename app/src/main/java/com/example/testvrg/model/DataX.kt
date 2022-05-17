package com.example.testvrg.model

data class DataX(
    val author: String,
    val author_fullname: String,
    val created: Int,
    val created_utc: Int,
    val id: String,
    val name: String,
    val num_comments: Int,
    val saved: Boolean,
    val subreddit_id: String,
    val thumbnail: String,
    val title: String,
    val url: String,
    val url_overridden_by_dest: String,
    val fallback_url: String,
    val is_video: Boolean
)