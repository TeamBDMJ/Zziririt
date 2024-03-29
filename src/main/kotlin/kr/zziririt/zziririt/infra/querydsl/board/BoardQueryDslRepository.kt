package kr.zziririt.zziririt.infra.querydsl.board

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardQueryDslRepository {

    fun findByPageable(pageable: Pageable): Page<BoardRowDto>

    fun findStreamers(): List<StreamerBoardRowDto>

    fun findBoardStatusToInactive(): List<Long>

    fun updateBoardStatusToInactive(inactiveBoardIdList: List<Long>)

    fun findActiveStatusBoards(pageable: Pageable): Page<BoardRowDto>

    fun findChildBoards(boardId: Long): List<ChildBoardRowDto>
}