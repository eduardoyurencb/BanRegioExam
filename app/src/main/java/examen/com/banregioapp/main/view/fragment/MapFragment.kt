package examen.com.banregioapp.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import examen.com.banregioapp.R
import examen.com.banregioapp.base.fragment.BaseFragment
import examen.com.banregioapp.databinding.FragmentMapBinding
import examen.com.corebanregio.model.BranchResponse


class MapFragment :
        BaseFragment(),
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private var binding: FragmentMapBinding? = null
    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private var arrayListBranches: ArrayList<BranchResponse>? = null
    private val ZOOM_CAMERA_MAP = 12.0f
    private val ATM = "C"
    private var mapFragmentIFace: MapFragmentIFace? = null
    private var location: LatLng? = null

    companion object {
        val TAG = MapFragment::class.java.simpleName
        fun newInstance(
                arrayListBranches: ArrayList<BranchResponse>,
                mapFragmentIFace: MapFragmentIFace,
                location: LatLng
        ): MapFragment {
            val mapFragment = MapFragment()
            mapFragment.arrayListBranches = arrayListBranches
            mapFragment.mapFragmentIFace = mapFragmentIFace
            mapFragment.location = location
            return mapFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentMapBinding.bind(
                    inflater.inflate(R.layout.fragment_map, container, false))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.mapFragmentIFace = mapFragmentIFace
        initializeMap(savedInstanceState)
    }

    private fun initializeMap(savedInstanceState: Bundle?) {
        mMapView = binding?.mapView
        mMapView?.onCreate(savedInstanceState)
        mMapView?.onResume()
        mMapView?.getMapAsync(this)
        try {
            MapsInitializer.initialize(activity?.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
        googleMap?.moveCamera(
                CameraUpdateFactory.newLatLng(
                        location
                )
        )
        googleMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        location,
                        ZOOM_CAMERA_MAP
                )
        )
        loadMarkers()
        googleMap?.setOnMarkerClickListener(this)
    }

    private fun loadMarkers() {
        arrayListBranches?.let {
            for ((index, item) in it.withIndex()) {
                val location = LatLng(item.latitud, item.longitud)
                googleMap?.let {
                    it.addMarker(getMarker(location, item)).tag = index
                }
            }
        }
    }

    private fun getMarker(location: LatLng, branchResponse: BranchResponse): MarkerOptions {
        var drawable: Int
        if (isATM(branchResponse))
            drawable = R.drawable.ic_pin
        else
            drawable = R.drawable.ic_location

        val marker = MarkerOptions().position(location)
                .icon(
                        examen.com.banregioapp.utils.ViewUtils.bitmapDescriptorFromVector(
                                context as Context,
                                drawable)
                )
        return marker
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.d(TAG, "onMarkerClick()")
        var index = p0?.tag as Int
        arrayListBranches?.let {
            if (index < it.size) {
                mapFragmentIFace?.onCLickMarker(it[index])
            }
        }
        return false
    }

    private fun isATM(branchResponse: BranchResponse) = branchResponse.tipo.equals(ATM)
}