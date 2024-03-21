package kr.zziririt.zziririt.infra.querydsl.iconshop

import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.IconShopRowDto
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface IconShopQueryDslRepository {
    fun searchBy(condition: IconSearchCondition, pageable: Pageable): PageImpl<IconShopRowDto>
}