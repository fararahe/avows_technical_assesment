package com.avows.home.ui.order_summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avows.domain.db.model.CartEntityDomain
import com.avows.home.R
import com.avows.home.databinding.ItemSummaryBinding
import com.avows.utility.extensions.getFormattedNumber
import com.avows.utility.extensions.loadImage
import com.avows.utility.extensions.toStringFormat

class SummaryAdapter(
    private var listData: MutableList<CartEntityDomain>,
) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemSummaryBinding.inflate(
                inflater,
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = listData[position]
        item.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(
        private val binding: ItemSummaryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: CartEntityDomain,
        ) = with(binding) {
            tvProductName.text = data.title
            ivProduct.loadImage(
                url = data.image,
                errorDrawable = R.drawable.ic_box,
                placeholderDrawable = R.drawable.ic_load
            )
            tvCategory.text = data.category
            tvQty.text = data.qty.toStringFormat()
            tvPrice.text = data.price.times(data.qty).getFormattedNumber()
        }
    }
}