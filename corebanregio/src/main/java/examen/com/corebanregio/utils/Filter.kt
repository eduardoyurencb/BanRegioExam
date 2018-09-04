package examen.com.corebanregio.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import examen.com.corebanregio.model.BranchResponse
import java.util.*
import kotlin.collections.ArrayList

class Filter {
    companion object {
        fun getFilterName(
                string: String,
                arrayList: ArrayList<BranchResponse>): ArrayList<BranchResponse> {
            var arrayListNew = ArrayList<BranchResponse>()
            for (item in arrayList) {
                if (item.nombre.toLowerCase().contains(string) || item.domicilio.toLowerCase().contains(string)) {
                    arrayListNew.add(item)
                }
            }
            return arrayListNew
        }

        fun getFilterUbication(
                location: Location,
                arrayList: ArrayList<BranchResponse>
        ): ArrayList<BranchResponse> {

            for (item in arrayList) {
                item.distanceBeetwenUser = getDistance(
                        location,
                        item
                )
            }
            Collections.sort(arrayList)
            return arrayList
        }

        private fun getDistance(location: Location, branchResponse: BranchResponse): Float {
            var locationB = Location("PointB")
            locationB.latitude = branchResponse.latitud
            locationB.longitude = branchResponse.longitud
            return location.distanceTo(locationB)
        }
    }
}