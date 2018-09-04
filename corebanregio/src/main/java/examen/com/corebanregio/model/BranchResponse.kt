package examen.com.corebanregio.model

import com.google.gson.annotations.SerializedName

data class BranchResponse(
        @SerializedName("ID") val id: Int,
        @SerializedName("tipo") val tipo: String,
        @SerializedName("NOMBRE") val nombre: String,
        @SerializedName("DOMICILIO") val domicilio: String,
        @SerializedName("HORARIO") val horario: String,
        @SerializedName("TELEFONO_PORTAL") val telefonoPortal: String,
        @SerializedName("TELEFONO_APP") val telefonoApp: String,
        @SerializedName("24_HORAS") val fullTime: String,
        @SerializedName("SABADOS") val sabados: String,
        @SerializedName("CIUDAD") val ciudad: String,
        @SerializedName("ESTADO") val estado: String,
        @SerializedName("Latitud") val latitud: Double,
        @SerializedName("Longitud") val longitud: Double,
        @SerializedName("Correo_Gerente") val correoGerente: String,
        @SerializedName("URL_FOTO") val urlFoto: String = " ",
        @SerializedName("Suc_Estado_Prioridad") val sucEstadoPrioridad: String,
        @SerializedName("Suc_Ciudad_Prioridad") val sucCiudadPrioridad: String,
        var distanceBeetwenUser: Float = 0.0f
) : Comparable<BranchResponse> {
    override fun compareTo(other: BranchResponse): Int {
        return distanceBeetwenUser.compareTo(other.distanceBeetwenUser)
    }

}