package kr.zziririt.zziririt.infra.querydsl.pointhistory.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class PointHistoryRowDto @QueryProjection constructor(
    val id: Long,
    val change: Long,
    val description: String,
    val createdAt: LocalDateTime,
)
