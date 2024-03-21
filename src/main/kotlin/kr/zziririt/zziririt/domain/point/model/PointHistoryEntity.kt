package kr.zziririt.zziririt.domain.point.model

import jakarta.persistence.*
import kr.zziririt.zziririt.domain.member.model.SocialMemberEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "point_history")
@SQLDelete(sql = "UPDATE point_history SET is_deleted = true WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
class PointHistoryEntity(
    // 거래포인트
    @Column(name = "change", nullable = false)
    var change: Long,

    // 거래 내역
    @Column(name = "description", nullable = false)
    var description: String,

    // 거래 일시
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    // 고객 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "social_member_id", nullable = false)
    val socialMemberEntity: SocialMemberEntity,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}