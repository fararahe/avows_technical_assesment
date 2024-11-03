package com.avows.home.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avows.domain.db.model.CartEntityDomain
import com.avows.home.R
import com.avows.home.databinding.ItemCartBinding
import com.avows.utility.extensions.getFormattedNumber
import com.avows.utility.extensions.loadImage
import com.avows.utility.extensions.setOnSafeClickListener
import com.avows.utility.extensions.toStringFormat

class CartAdapter(
    private var listData: MutableList<CartEntityDomain>,
    private var onPlusClicked: (Pair<Int, Int>) -> Unit,
    private var onMinusClicked: (Pair<Int, Int>) -> Unit,
    private val onDeleteClicked: (Int) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemCartBinding.inflate(
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
        private val binding: ItemCartBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CartEntityDomain) = with(binding) {
            tvProductName.text = data.title
            ivProduct.loadImage(
                url = data.image,
                errorDrawable = R.drawable.ic_box,
                placeholderDrawable = R.drawable.ic_load
            )
            tvCategory.text = data.category
            tvCount.text = data.qty.toStringFormat()
            tvPrice.text = data.price.getFormattedNumber()

            ibPlus.setOnClickListener {
                onPlusClicked.invoke(
                    Pair(
                        data.productId,
                        (tvCount.text.toString().toIntOrNull() ?: 1).plus(1)
                    )
                )
            }

            ibMinus.setOnClickListener {
                val currentCount = tvCount.text.toString().toIntOrNull() ?: 1
                if (currentCount > 1) {
                    onMinusClicked.invoke(
                        Pair(data.productId, currentCount.minus(1))
                    )
                }
            }

            ibDelete.setOnSafeClickListener {
                onDeleteClicked.invoke(data.productId)
            }
        }
    }

    fun getCurrentData(): List<CartEntityDomain> {
        return listData
    }

    fun updateData(newData: List<CartEntityDomain>) {
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    fun updateItem(updatedItem: CartEntityDomain) {
        val index = listData.indexOfFirst { it.productId == updatedItem.productId }
        if (index != -1) {
            listData[index] = updatedItem
            notifyItemChanged(index)
        }
    }
}