package examen.com.banregioapp.main.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import examen.com.banregioapp.R
import examen.com.banregioapp.base.fragment.BaseFragment
import examen.com.banregioapp.databinding.FragmentSearchBranchBinding
import examen.com.banregioapp.main.adapter.BranchListAdapter
import examen.com.corebanregio.model.BranchResponse
import examen.com.corebanregio.utils.Filter

class SearchBranchFragment :
        BaseFragment(),
        TextWatcher {

    private var binding: FragmentSearchBranchBinding? = null
    private var arrayList = ArrayList<BranchResponse>()
    private var searchBranchIFace: SearchBranchIFace? = null

    companion object {
        val TAG = SearchBranchFragment::class.java.simpleName
        fun newInstance(
                arrayList: ArrayList<BranchResponse>,
                searchBranchIFace: SearchBranchIFace?
        ): SearchBranchFragment {
            val searchBranchFragment = SearchBranchFragment()
            searchBranchFragment.arrayList = arrayList
            searchBranchFragment.searchBranchIFace = searchBranchIFace
            return searchBranchFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentSearchBranchBinding.bind(
                    inflater.inflate(R.layout.fragment_search_branch, container, false))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.editTextSearch?.addTextChangedListener(this)
        recyclerView = binding?.recyclerView
        recyclerView?.setHasFixedSize(true)
        lManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
        recyclerView?.layoutManager = lManager
        adapter = BranchListAdapter(
                arrayList,
                context,
                searchBranchIFace
        )
        recyclerView?.adapter = adapter
    }


    override fun afterTextChanged(s: Editable?) {
        Log.d(TAG,"afterTextChanged()")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.d(TAG,"beforeTextChanged()")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d(TAG,"onTextChanged()")
        binding?.editTextSearch?.text?.let {
            (adapter as BranchListAdapter).setData(Filter.getFilterName(it.toString(), arrayList))
        }

    }
}