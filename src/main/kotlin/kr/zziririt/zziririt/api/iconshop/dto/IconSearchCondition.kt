package kr.zziririt.zziririt.api.iconshop.dto

import kr.zziririt.zziririt.api.iconshop.dto.valid.ValidSaleStatusType
import org.springframework.validation.annotation.Validated

@Validated
data class IconSearchCondition(
    @field:ValidSaleStatusType
    val searchType: String?,
    val searchTerm: String?,
    val page: String = "1",
    val size: String = "30",
)