package kr.zziririt.zziririt.infra.querydsl.post.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class PostRowDto @QueryProjection constructor(
    val postId: Long,
    val category: String,
    val title: String,
    val memberId: Long,
    val nickname: String,
    val hit: Long,
    val createdAt: LocalDateTime
)