package examen.com.corebanregio.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("user") val user: String,
        @SerializedName("password") val password: String
)