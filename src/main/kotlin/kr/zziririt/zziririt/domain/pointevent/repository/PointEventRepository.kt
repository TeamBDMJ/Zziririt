package kr.zziririt.zziririt.domain.pointevent.repository

import kr.zziririt.zziririt.domain.pointevent.model.PointEventEntity
import org.springframework.data.repository.CrudRepository

interface PointEventRepository: CrudRepository<PointEventEntity, Long> {
}