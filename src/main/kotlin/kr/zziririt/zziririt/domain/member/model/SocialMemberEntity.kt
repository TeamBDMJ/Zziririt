package kr.zziririt.zziririt.domain.member.model

import jakarta.persistence.*
import kr.zziririt.zziririt.global.entity.BaseTimeEntity
import kr.zziririt.zziririt.global.exception.ErrorCode
import kr.zziririt.zziririt.global.exception.RestApiException

@Entity
@Table(name = "social_member")
class SocialMemberEntity(

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,

    @Column(name = "provider_id", nullable = false)
    val providerId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    var memberRole: MemberRole = MemberRole.VIEWER,

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", nullable = false)
    var memberStatus: MemberStatus = MemberStatus.NORMAL,

    //포인트
    @Column(name = "point", nullable = false)
    var point: Long = 0L,


    ) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    init {
        if (this.point < 0L) {
            throw RestApiException(ErrorCode.POINT_POLICY_VIOLATION)
        }
    }


    fun toBoardManager() {
        this.memberRole = MemberRole.BOARD_MANAGER
    }

    fun toViewer() {
        this.memberRole = MemberRole.VIEWER
    }

    fun toBanned() {
        this.memberStatus = MemberStatus.BANNED
    }

    companion object {
        fun ofNaver(
            providerId: String,
            nickname: String,
            email: String,
            provider: String,
            memberRole: MemberRole,
            memberStatus: MemberStatus
        ): SocialMemberEntity {
            return SocialMemberEntity(
                email = email,
                nickname = nickname,
                provider = OAuth2Provider.NAVER,
                providerId = providerId,
                memberRole = memberRole,
                memberStatus = memberStatus
            )
        }
    }
}