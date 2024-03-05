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
    @JoinColumn(name = "social_member_id", nullable = false)
    val socialMember: SocialMemberEntity,

    @Column(name = "board_name", nullable = false)
    var boardName: String,

    // 나중에 누적 시청자 수로 스트리머 게시판 생성 허가하는 로직 때 사용하기
    @Column
    var accumulateCount: Int = 0
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun update(boardName: String) {
        this.boardName = boardName
    }
}