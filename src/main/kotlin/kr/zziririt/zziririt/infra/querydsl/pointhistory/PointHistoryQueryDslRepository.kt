package kr.zziririt.zziririt.infra.querydsl.pointhistory

import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.PointHistoryRowDto

interface PointHistoryQueryDslRepository {
    fun userFindPointHistory(memberId: Long): List<PointHistoryRowDto>
}