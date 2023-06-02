package woowacourse.shopping.ui.model

import java.io.Serializable

data class ProductModel(
    val id: Int,
    val picture: String,
    val title: String,
    val price: Int
) : Serializable
