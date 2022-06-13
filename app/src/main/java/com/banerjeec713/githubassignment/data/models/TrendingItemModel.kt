package com.banerjeec713.githubassignment.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TrendingItemModel(
    @field:Expose @field:SerializedName("author") var author:String,
    @field:Expose @field:SerializedName("name") var name:String,
    @field:Expose @field:SerializedName("avatar") var avatar:String,
    @field:Expose @field:SerializedName("url") var url:String,
    @field:Expose @field:SerializedName("description") var description:String,
    @field:Expose @field:SerializedName("language") var language:String,
    @field:Expose @field:SerializedName("languageColor") var languageColor:String,
    @field:Expose @field:SerializedName("stars") var stars:Int,
    @field:Expose @field:SerializedName("forks") var forks:Int,
    @field:Expose @field:SerializedName("currentPeriodStars") var currentPeriodStars:Int
): Serializable