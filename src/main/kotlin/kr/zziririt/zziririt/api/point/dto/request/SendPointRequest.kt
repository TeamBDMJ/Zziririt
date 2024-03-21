package kr.zziririt.zziririt.api.point.dto.request

import kr.zziririt.zziririt.domain.member.model.SocialMemberEntity
import kr.zziririt.zziririt.domain.point.model.PointHistoryEntity

data class SendPointRequest(
    val memberEmail: String,
    val sendPoint: Long,
) {
    fun to(socialMemberEntity: SocialMemberEntity) = PointHistoryEntity(
        change = socialMemberEntity.point - sendPoint,
        description = "포인트 전송",
        socialMemberEntity = socialMemberEntity,
    )

    fun toReceivedMember(socialMemberEntity: SocialMemberEntity) = PointHistoryEntity(
        change = sendPoint,
        description = "포인트 획득",
        socialMemberEntity = socialMemberEntity
    )
}
