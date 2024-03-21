package kr.zziririt.zziririt.domain.point.repository

import kr.zziririt.zziririt.domain.point.model.PointHistoryEntity
import kr.zziririt.zziririt.infra.jpa.point.PointHistoryJpaRepository
import kr.zziririt.zziririt.infra.querydsl.pointhistory.PointHistoryQueryDslRepositoryImpl
import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.PointHistoryRowDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepositoryImpl(
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
    private val pointHistoryQueryDslRepositoryImpl: PointHistoryQueryDslRepositoryImpl
): PointHistoryRepository {
    override fun findByIdOrNull(id: Long): PointHistoryEntity? = pointHistoryJpaRepository.findByIdOrNull(id)

    override fun save(entity: PointHistoryEntity): PointHistoryEntity = pointHistoryJpaRepository.save(entity)

    override fun delete(entity: PointHistoryEntity) = pointHistoryJpaRepository.delete(entity)

    override fun findAll(): List<PointHistoryEntity> = pointHistoryJpaRepository.findAll()

    override fun findAllById(idList: List<Long>): List<PointHistoryEntity> = pointHistoryJpaRepository.findAllById(idList)

    override fun userFindPointHistory(memberId: Long): List<PointHistoryRowDto>  = pointHistoryQueryDslRepositoryImpl.userFindPointHistory(memberId)

}