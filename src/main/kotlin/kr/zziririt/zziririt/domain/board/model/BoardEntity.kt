package kr.zziririt.zziririt.domain.board.model

import jakarta.persistence.*
import kr.zziririt.zziririt.domain.member.model.SocialMemberEntity
import kr.zziririt.zziririt.global.entity.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "board")
@SQLDelete(sql = "UPDATE board SET is_deleted = true WHERE id = ?")
@SQLRestriction(value = "is_deleted = false")
class BoardEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val parent: BoardEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "social_member_id", nullable = true)
    val socialMember: SocialMemberEntity? = null,

    @Column(name = "board_name", nullable = false)
    var boardName: String,

    @Column(name = "board_url", nullable = false)
    var boardUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type")
    var boardType: BoardType,

    @Enumerated(EnumType.STRING)
    @Column(name = "board_act_status")
    var boardActStatus: BoardActStatus = BoardActStatus.ACTIVE,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun update(boardName: String) {
        this.boardName = boardName
    }
}