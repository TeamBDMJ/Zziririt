package kr.zziririt.zziririt.api.point.dto.request

import kr.zziririt.zziririt.domain.member.model.SocialMemberEntity
import kr.zziririt.zziririt.domain.point.model.PointHistoryEntity

data class BuyIconRequest(
    val usePoint: Long,
) {
    fun to(socialMemberEntity: SocialMemberEntity) = PointHistoryEntity(
        change = socialMemberEntity.point - usePoint,
        description = "아이콘 구매",
        socialMemberEntity = socialMemberEntity,

    )
}
