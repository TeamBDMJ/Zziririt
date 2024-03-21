package kr.zziririt.zziririt.api.iconshop.dto.request

import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus

data class ChangeSaleStatusRequest(
    val saleStatus: SaleStatus,
)