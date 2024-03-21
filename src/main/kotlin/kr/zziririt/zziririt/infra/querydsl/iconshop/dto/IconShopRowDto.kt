package kr.zziririt.zziririt.infra.querydsl.iconshop.dto

import com.querydsl.core.annotations.QueryProjection
import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus
import java.io.Serializable

data class IconShopRowDto @QueryProjection constructor(
    val iconId: Long,
    val price: Long,
    val iconQuantity: Int,
    val saleStatus: SaleStatus
) : Serializable