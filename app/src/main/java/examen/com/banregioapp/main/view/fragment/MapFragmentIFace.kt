package examen.com.banregioapp.main.view.fragment

import examen.com.corebanregio.model.BranchResponse

interface MapFragmentIFace {
    fun onCLickMarker(branchResponse: BranchResponse)
    fun onClickSearchOption()
}