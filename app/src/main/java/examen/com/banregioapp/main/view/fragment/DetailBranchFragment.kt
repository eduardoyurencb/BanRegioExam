package examen.com.banregioapp.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import examen.com.banregioapp.R
import examen.com.banregioapp.base.fragment.BaseFragment
import examen.com.banregioapp.databinding.FragmentDetailBranchBinding
import examen.com.banregioapp.utils.FontUtils
import examen.com.banregioapp.utils.ViewUtils
import examen.com.corebanregio.model.BranchResponse

class DetailBranchFragment : BaseFragment() {
    private var binding: FragmentDetailBranchBinding? = null
    private var branchResponse: BranchResponse? = null

    companion object {
        val TAG = DetailBranchFragment::class.java.simpleName
        fun newInstance(branchResponse: BranchResponse): DetailBranchFragment {
            val detailBranchFragment = DetailBranchFragment()
            detailBranchFragment.branchResponse = branchResponse
            return detailBranchFragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDetailBranchBinding.bind(
                    inflater.inflate(R.layout.fragment_detail_branch, container, false))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.branchResponse = branchResponse
        binding?.context = context
        binding?.font = FontUtils.FONTAWESOME

        branchResponse?.urlFoto?.let {
            ViewUtils.loadImgWithPicasso(
                    context,
                    it,
                    binding?.imageViewBranch)
        }
    }
}