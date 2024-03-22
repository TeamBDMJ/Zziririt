package kr.zziririt.zziririt.api.pointevent.service

import jakarta.transaction.Transactional
import kr.zziririt.zziririt.api.pointevent.dto.request.CreatePointEventRequest
import kr.zziririt.zziririt.api.pointevent.dto.request.UpdatePointEventRequest
import kr.zziririt.zziririt.domain.member.repository.SocialMemberRepository
import kr.zziririt.zziririt.domain.pointevent.repository.PointEventRepository
import kr.zziririt.zziririt.global.exception.ErrorCode
import kr.zziririt.zziririt.global.exception.ModelNotFoundException
import kr.zziririt.zziririt.global.exception.RestApiException
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PointEventService (
    private val memberRepository: SocialMemberRepository,
    private val pointEventRepository: PointEventRepository,
){

    @Transactional
    fun createPointEvent(request: CreatePointEventRequest, userPrincipal: UserPrincipal) {

        memberRepository.findByIdOrNull(userPrincipal.memberId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        val startDate = LocalDateTime.now().plusMinutes(request.period.toLong())
        val req = request.toEntity(startDate)
        pointEventRepository.save(req)
    }

    @Transactional
    fun updatePointEventStatus(pointEventId: Long, request: UpdatePointEventRequest, userPrincipal: UserPrincipal) {

        memberRepository.findByIdOrNull(userPrincipal.memberId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        val pointEventCheck = pointEventRepository.findByIdOrNull(pointEventId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        pointEventCheck.changeStatus(request.status)
    }

    @Transactional
    fun participateEvent(pointEventId: Long , userPrincipal: UserPrincipal) {
        val checkMember = memberRepository.findByIdOrNull(userPrincipal.memberId) ?: throw RestApiException(ErrorCode.POINT_POLICY_VIOLATION)
        checkMember.verifyPoint(checkMember.point)

        val checkEvent = pointEventRepository.findByIdOrNull(pointEventId) ?: throw RestApiException(ErrorCode.MODEL_NOT_FOUND)
        checkEvent.addNowMember()
    }

    @Transactional
    fun deletePointEvent(pointEventId: Long, userPrincipal: UserPrincipal) {
        memberRepository.findByIdOrNull(userPrincipal.memberId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)
        val pointEventCheck = pointEventRepository.findByIdOrNull(pointEventId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        pointEventRepository.delete(pointEventCheck)
    }

    fun finishPointEvent() {
        TODO("스케쥴러로 만료 시간이 되면 event status를 finish로")
        TODO("이거하려면 먼저 대상 List를 찾는 queryDsl 필요")

    }

}