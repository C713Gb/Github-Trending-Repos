package com.banerjeec713.githubassignment.data.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class LicenseModel(@field:Expose @field:SerializedName("name") var name: String) : Serializable