package kr.zziririt.zziririt.api.iconshop.dto.response

import kr.zziririt.zziririt.domain.iconshop.model.IconShopEntity
import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus

data class IconShopResponse(
    val iconShopId: Long,
    val price: Long,
    val iconQuantity: Int,
    val saleStatus: SaleStatus,
    val iconId: Long,
) {
    companion object {
        fun from(iconShopEntity: IconShopEntity): IconShopResponse = IconShopResponse(
            iconShopId = iconShopEntity.id,
            price = iconShopEntity.price,
            iconQuantity = iconShopEntity.iconQuantity,
            saleStatus = iconShopEntity.saleStatus,
            iconId = iconShopEntity.icon.id
        )
    }
}