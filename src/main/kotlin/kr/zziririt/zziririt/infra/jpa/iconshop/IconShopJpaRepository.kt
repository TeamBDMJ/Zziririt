package kr.zziririt.zziririt.infra.jpa.iconshop

import kr.zziririt.zziririt.domain.iconshop.model.IconShopEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IconShopJpaRepository : JpaRepository<IconShopEntity, Long> {
}