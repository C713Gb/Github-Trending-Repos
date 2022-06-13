package com.banerjeec713.githubassignment.data.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class OwnerModel(
    @field:Expose
    @field:SerializedName("login")
    var name: String,

    @field:Expose
    @field:SerializedName(
        "avatar_url"
    )
    var avatar_url: String,

    @field:Expose
    @field:SerializedName("html_url")
    var html_url: String
) : Serializable