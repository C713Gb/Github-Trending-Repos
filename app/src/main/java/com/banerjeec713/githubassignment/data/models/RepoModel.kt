package com.banerjeec713.githubassignment.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RepoModel(
    @field:Expose @field:SerializedName("total_count") var count: Int,
    @field:Expose @field:SerializedName(
        "incomplete_results"
    ) var isResults: Boolean,
    @field:Expose @field:SerializedName("items") var items: List<ItemModel>
) : Serializable