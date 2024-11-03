package com.avows.data.db.repository_impl

import com.avows.data.db.data.dao.ListProductDao
import com.avows.data.db.data.entity.ListProductEntity
import com.avows.domain.db.model.ListProductEntityDomain
import com.avows.domain.db.repository.ListProductRepository
import javax.inject.Inject

class ListProductRepositoryImpl @Inject constructor(
    private val listProductDao: ListProductDao
) : ListProductRepository {

    override suspend fun insertProduct(product: List<ListProductEntityDomain>) =
        listProductDao.insertProduct(
            product.map {
                ListProductEntity(
                    billId = it.billId,
                    productId = it.productId,
                    title = it.title,
                    category = it.category,
                    description = it.description,
                    image = it.image,
                    price = it.price,
                    qty = it.qty,
                    totalPrice = it.totalPrice
                )
            }
        )

    override suspend fun getProductsById(productId: Int): ListProductEntityDomain? =
        listProductDao.getProductById(productId)?.let { data ->
            ListProductEntityDomain(
                listProductId = data.productListId,
                billId = data.billId,
                productId = data.productId,
                title = data.title,
                category = data.category,
                description = data.description,
                image = data.image,
                price = data.price,
                qty = data.qty,
                totalPrice = data.totalPrice
            )
        }

    override suspend fun getAllProduct(): List<ListProductEntityDomain> =
        listProductDao.getAllProduct()?.map { data ->
            ListProductEntityDomain(
                listProductId = data.productListId,
                billId = data.billId,
                productId = data.productId,
                title = data.title,
                category = data.category,
                description = data.description,
                image = data.image,
                price = data.price,
                qty = data.qty,
                totalPrice = data.totalPrice
            )
        } ?: emptyList()
}