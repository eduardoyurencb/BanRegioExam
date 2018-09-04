package examen.com.banregioapp.main.view

import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.model.LatLng
import examen.com.banregioapp.R
import examen.com.banregioapp.base.activity.BaseActivity
import examen.com.banregioapp.databinding.ActivityMainBinding
import examen.com.banregioapp.main.presenter.MainPresenter
import examen.com.banregioapp.main.view.fragment.*
import examen.com.corebanregio.model.BranchResponse
import examen.com.corebanregio.utils.Filter

class MainActivity : BaseActivity(),
        MainView,
        MapFragmentIFace,
        SearchBranchIFace {

    private var binding: ActivityMainBinding? = null
    private var mapFragment: MapFragment? = null
    private var detailBranchFragment: DetailBranchFragment? = null
    private var searchBranchFragment: SearchBranchFragment? = null
    private var arrayList = ArrayList<BranchResponse>()
    private var mainPresenter: MainPresenter? = null
    private var toolbar: Toolbar? = null
    private var menuItem: MenuItem? = null
    private lateinit var location: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        )
        location = getIntent().getExtras().getParcelable("location");
        onBindingView()
        mainPresenter?.getBranchs()
    }

    override fun onBindingView() {
        mainPresenter = MainPresenter(this)
        mainPresenter?.bindView(this)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun addMapFragment(t: ArrayList<BranchResponse>) {
        var aLocation = Location("PointA")
        aLocation.latitude = location.latitude
        aLocation.longitude = location.longitude
        arrayList = Filter.getFilterUbication(aLocation, t)
        val tag = MapFragment.TAG
        val fr = supportFragmentManager.findFragmentByTag(tag)
        val tx: FragmentTransaction
        if (fr == null) {
            mapFragment = MapFragment.newInstance(
                    t,
                    this,
                    location)
            tx = supportFragmentManager.beginTransaction()
            tx.replace(R.id.frame_layout_main, mapFragment, tag)
            tx.commit()
        }
    }

    override fun onCLickMarker(branchResponse: BranchResponse) {
        addDetailBranchFragment(branchResponse)
    }

    override fun onClickSearchOption() {
        addSearchBranchFragment()
    }

    private fun addDetailBranchFragment(branchResponse: BranchResponse) {
        val tag = DetailBranchFragment.TAG
        val fr = supportFragmentManager.findFragmentByTag(tag)
        val tx: FragmentTransaction
        if (fr == null) {
            detailBranchFragment = DetailBranchFragment.newInstance(branchResponse)
            tx = supportFragmentManager.beginTransaction()
            tx.add(R.id.frame_layout_main, detailBranchFragment, tag)
            tx.addToBackStack(tag)
            tx.commit()
        }
        setVisibleMenuItem(false)
    }

    private fun addSearchBranchFragment() {
        val tag = SearchBranchFragment.TAG
        val fr = supportFragmentManager.findFragmentByTag(tag)
        val tx: FragmentTransaction
        if (fr == null) {
            searchBranchFragment = SearchBranchFragment.newInstance(
                    arrayList,
                    this
            )
            tx = supportFragmentManager.beginTransaction()
            tx.add(R.id.frame_layout_main, searchBranchFragment, tag)
            tx.addToBackStack(tag)
            tx.commit()
        }
        setVisibleMenuItem(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        menuItem = menu.findItem(R.id.search)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onClickSearchOption()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var f = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main)
        f?.let {
            if (f is MapFragment)
                setVisibleMenuItem(true)
        }
    }

    override fun onClickItemBranch(branchResponse: BranchResponse) {
        addDetailBranchFragment(branchResponse)
    }

    fun setVisibleMenuItem(flag: Boolean) = menuItem?.setVisible(flag)
}
