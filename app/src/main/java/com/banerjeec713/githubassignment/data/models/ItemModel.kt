package com.banerjeec713.githubassignment.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ItemModel(
    @field:Expose @field:SerializedName("name") var name: String,
    @field:Expose @field:SerializedName(
        "html_url"
    ) var html_url: String,
    @field:Expose @field:SerializedName("clone_url") var clone_url: String,
    @field:Expose @field:SerializedName(
        "description"
    ) var description: String,
    @field:Expose @field:SerializedName("language") var language: String,
    @field:Expose @field:SerializedName(
        "license"
    ) var licenses: LicenseModel,
    @field:Expose @field:SerializedName("owner") var owners: OwnerModel,
    @field:Expose @field:SerializedName(
        "stargazers_count"
    ) var star_count: Int
) : Serializable