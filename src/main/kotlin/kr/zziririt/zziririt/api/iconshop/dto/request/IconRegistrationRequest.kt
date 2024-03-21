package kr.zziririt.zziririt.api.iconshop.dto.request

import kr.zziririt.zziririt.domain.icon.model.IconEntity
import kr.zziririt.zziririt.domain.iconshop.model.IconShopEntity
import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus

data class IconRegistrationRequest(
    val iconId: Long,
    val price: Long,
    val iconQuantity: Int,
    val saleStatus: SaleStatus,
) {
    fun toEntity(icon: IconEntity): IconShopEntity = IconShopEntity(
        icon = icon,
        price = price,
        iconQuantity = iconQuantity,
        saleStatus = saleStatus,
    )
}