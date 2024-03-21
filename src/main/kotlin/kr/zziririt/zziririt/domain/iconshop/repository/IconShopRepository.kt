package kr.zziririt.zziririt.domain.iconshop.repository

import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.domain.iconshop.model.IconShopEntity
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.IconShopRowDto
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface IconShopRepository {
    fun searchBy(condition: IconSearchCondition, pageable: Pageable): PageImpl<IconShopRowDto>

    fun findByIdOrNull(id: Long): IconShopEntity?

    fun save(entity: IconShopEntity): IconShopEntity

    fun delete(entity: IconShopEntity)
}