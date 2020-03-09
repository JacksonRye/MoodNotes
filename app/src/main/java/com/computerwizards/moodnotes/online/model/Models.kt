package com.computerwizards.moodnotes.online.model

import com.computerwizards.moodnotes.database.Reddit


// Advice Models
data class Response(
    val slip: FieldItems? = null
)

data class FieldItems(
    val advice: String? = null,
    val slip_id: String? = null
)

// Reddit models
data class RedditResponse(
    val data: ResponseData
)

data class ResponseData(
    val children: List<ChildData>
)

data class ChildData(
    val data: SingleData
)

data class SingleData(
    val subreddit: String,
    val title: String,
    val thumbnail: String

)

data class RedditItem(val title: String, val subreddit: String, val thumbnail: String)


fun RedditResponse.asDomainModel(): List<RedditItem> {
    return data.children.map {
        RedditItem(
            it.data.title,
            it.data.subreddit,
            it.data.thumbnail
        )
    }
}


fun RedditResponse.asDatabaseModel(): List<Reddit> {
    return data.children.map {
        Reddit(
            it.data.title,
            it.data.subreddit,
            it.data.thumbnail
        )
    }
}

