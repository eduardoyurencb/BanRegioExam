package examen.com.banregioapp.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import examen.com.banregioapp.databinding.NodeBrancheBinding
import examen.com.banregioapp.main.view.fragment.SearchBranchIFace
import examen.com.banregioapp.utils.FontUtils
import examen.com.banregioapp.utils.ViewUtils
import examen.com.corebanregio.model.BranchResponse

class BranchListAdapter(
        var items: ArrayList<BranchResponse>,
        val context: Context?,
        val searchBranchIFace: SearchBranchIFace?
) : RecyclerView.Adapter<BranchListAdapter.BranchListViewHolder>() {

    class BranchListViewHolder(
            val nodeBrancheBinding: NodeBrancheBinding
    ) : RecyclerView.ViewHolder(nodeBrancheBinding.root)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchListAdapter.BranchListViewHolder {
        val binding = NodeBrancheBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return BranchListAdapter.BranchListViewHolder(binding)
    }

    fun setData(itemsNew: ArrayList<BranchResponse>) {
        if (itemsNew != null)
            this.items = itemsNew
        else
            items = ArrayList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BranchListAdapter.BranchListViewHolder, position: Int) {
        ViewUtils.loadImgWithPicasso(
                context,
                items[position].urlFoto,
                holder.nodeBrancheBinding.imageViewBranch)

        holder.nodeBrancheBinding.searchBranchIFace = searchBranchIFace
        holder.nodeBrancheBinding.branchResponse = items[position]
        holder.nodeBrancheBinding.context = context
        holder.nodeBrancheBinding.font = FontUtils.FONTAWESOME
    }
}