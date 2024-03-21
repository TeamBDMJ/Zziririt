package kr.zziririt.zziririt.infra.jpa.point

import kr.zziririt.zziririt.domain.point.model.PointHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryJpaRepository: JpaRepository<PointHistoryEntity, Long> {
}