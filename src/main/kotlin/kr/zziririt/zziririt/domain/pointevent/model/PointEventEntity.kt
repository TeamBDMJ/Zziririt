package kr.zziririt.zziririt.domain.pointevent.model

import jakarta.persistence.*
import kr.zziririt.zziririt.api.pointevent.dto.EventStatus
import kr.zziririt.zziririt.domain.member.model.SocialMemberEntity
import kr.zziririt.zziririt.global.entity.BaseEntity
import org.checkerframework.checker.units.qual.C
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "point_event")
@SQLDelete(sql = "UPDATE point_event SET is_deleted = true WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
class PointEventEntity(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "limit_member", nullable = false)
    var limitMember: Int,

    @Column(name = "admission_point", nullable = false)
    var admissionPoint: Long,

    @Column(name = "now_member", nullable = false)
    var nowMember: Int = 0,

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    var eventStatus: EventStatus = EventStatus.PREPARE,

    @ManyToOne
    @JoinColumn(name = "social_member_id", nullable = true)
    val member: SocialMemberEntity? = null

): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addNowMember() {
        this.nowMember ++

        if(this.limitMember == this.nowMember) {
            this.eventStatus = EventStatus.FINISH
        }
    }

    fun changeStatus(status: EventStatus) {
        this.eventStatus = status
    }

}