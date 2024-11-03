package com.avows.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avows.domain.home.model.response.ProductDomain
import com.avows.home.R
import com.avows.home.databinding.ItemProductBinding
import com.avows.utility.extensions.getFormattedNumber
import com.avows.utility.extensions.loadImage

class ProductAdapter(
    private var listData: MutableList<ProductDomain>? = null,
    private val onClickItem: (ProductDomain) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var originalData: List<ProductDomain> = listData ?: emptyList()

    override fun getItemCount(): Int = listData?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemProductBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData?.get(position)
        item?.let { holder.bind(it) }
    }

    inner class ViewHolder(
        private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductDomain) {
            binding.apply {
                tvProductName.text = data.title

                tvPrice.text = data.price.getFormattedNumber()
                ivProduct.loadImage(
                    url = data.image,
                    errorDrawable = R.drawable.ic_box,
                    placeholderDrawable = R.drawable.ic_load
                )

                root.setOnClickListener {
                    onClickItem.invoke(data)
                }
            }
        }
    }

    fun updateData(newData: List<ProductDomain>) {
        originalData = newData
        listData = newData.toMutableList()
        notifyDataSetChanged()
    }

    fun filterByCategory(category: String) {
        listData = if (category.isEmpty()) {
            originalData.toMutableList()
        } else {
            originalData.filter { it.category.equals(category, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}