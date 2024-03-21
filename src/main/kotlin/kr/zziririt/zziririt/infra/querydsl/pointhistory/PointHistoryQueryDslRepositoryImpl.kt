package kr.zziririt.zziririt.infra.querydsl.pointhistory

import kr.zziririt.zziririt.domain.point.model.QPointHistoryEntitiy
import kr.zziririt.zziririt.infra.querydsl.QueryDslSupport
import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.PointHistoryRowDto
import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.QPointHistoryRowDto
import org.springframework.stereotype.Repository

@Repository
class PointHistoryQueryDslRepositoryImpl: PointHistoryQueryDslRepository, QueryDslSupport() {
    private val pointHistory = QPointHistoryEntitiy.pointHistoryEntitiy

    override fun userFindPointHistory(memberId: Long): List<PointHistoryRowDto> {
        return queryFactory.select(QPointHistoryRowDto(
            pointHistory.id,
            pointHistory.change,
            pointHistory.description,
            pointHistory.createdAt
            )
        )
            .from(pointHistory)
            .where(pointHistory.socialMemberEntity.id.eq(memberId))
            .orderBy(pointHistory.createdAt.desc())
            .fetch()
    }



}