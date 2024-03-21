package kr.zziririt.zziririt.domain.iconshop.repository

import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.domain.iconshop.model.IconShopEntity
import kr.zziririt.zziririt.infra.jpa.iconshop.IconShopJpaRepository
import kr.zziririt.zziririt.infra.querydsl.iconshop.IconShopQueryDslRepository
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.IconShopRowDto
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class IconShopRepositoryImpl(
    private val iconShopQueryDslRepository: IconShopQueryDslRepository,
    private val iconShopJpaRepository: IconShopJpaRepository,
) : IconShopRepository {
    override fun searchBy(condition: IconSearchCondition, pageable: Pageable): PageImpl<IconShopRowDto> =
        iconShopQueryDslRepository.searchBy(condition, pageable)

    override fun findByIdOrNull(id: Long): IconShopEntity? = iconShopJpaRepository.findByIdOrNull(id)

    override fun save(entity: IconShopEntity): IconShopEntity = iconShopJpaRepository.save(entity)

    override fun delete(entity: IconShopEntity) = iconShopJpaRepository.delete(entity)
}