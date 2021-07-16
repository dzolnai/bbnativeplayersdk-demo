package com.bluebillywig.bbnativeplayersdk_demo.model

data class Item(
    val assets: String,
    val author: String,
    val cat: List<String>,
    val copyrightSort: String,
    val createddate: String,
    val dar_string: String,
    val deeplink_string: String,
    val description: String,
    val gendeeplink_string: String,
    val height_int: Int,
    val id: String,
    val importSource_string: String,
    val importUrl_string: String,
    val length_int: Int,
    val mainthumbnail_string: String,
    val mediatype: String,
    val modifieddate: String,
    val originalHeight_int: Int,
    val originalWidth_int: Int,
    val originalfilename: String,
    val parentid: String,
    val parentpublicationid: String,
    val publication: List<String>,
    val publicationid: Int,
    val published_date: String,
    val score: Double,
    val sourceid_string: String,
    val sourcetype: String,
    val src_string: String,
    val status: String,
    val subtitles_string: String,
    val subtitletracks_string: String,
    val thumbnails: String,
    val title: String,
    val totalviews_int: Int,
    val transcodingFinished_string: String,
    val type: String,
    val usetype: String,
    val views_int: Int,
    val width_int: Int,
    val youtubeImportId_string: String
)