package kr.zziririt.zziririt.domain.point.repository

import kr.zziririt.zziririt.domain.point.model.PointHistoryEntity
import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.PointHistoryRowDto

interface PointHistoryRepository {
    fun findByIdOrNull(id: Long): PointHistoryEntity?

    fun save(entity: PointHistoryEntity): PointHistoryEntity

    fun delete(entity: PointHistoryEntity)

    fun findAll(): List<PointHistoryEntity>

    fun findAllById(idList: List<Long>): List<PointHistoryEntity>

    fun userFindPointHistory(memberId: Long): List<PointHistoryRowDto>
}