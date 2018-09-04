package examen.com.banregioapp.base.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

open class BaseFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var adapter: RecyclerView.Adapter<*>? = null
    var lManager: LinearLayoutManager? = null
}