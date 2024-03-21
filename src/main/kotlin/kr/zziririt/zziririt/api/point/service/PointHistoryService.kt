package kr.zziririt.zziririt.api.point.service

import kr.zziririt.zziririt.api.point.dto.request.BuyIconRequest
import kr.zziririt.zziririt.api.point.dto.request.SendPointRequest
import kr.zziririt.zziririt.domain.member.repository.SocialMemberRepository
import kr.zziririt.zziririt.domain.point.repository.PointHistoryRepository
import kr.zziririt.zziririt.global.exception.ErrorCode
import kr.zziririt.zziririt.global.exception.RestApiException
import kr.zziririt.zziririt.infra.querydsl.pointhistory.dto.PointHistoryRowDto
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PointHistoryService(
    private val pointHistoryRepository: PointHistoryRepository,
    private val socialMemberRepository: SocialMemberRepository

) {
    @Transactional
    fun buyIcon(buyIconRequest: BuyIconRequest, userPrincipal: UserPrincipal) {
        val findMember = socialMemberRepository.findByIdOrNull(userPrincipal.memberId) ?: throw RestApiException(ErrorCode.MODEL_NOT_FOUND)

        findMember.verifyPoint()

        val findPointHistory = buyIconRequest.to(findMember)

        findMember.updatePoint(findPointHistory.change)

        pointHistoryRepository.save(findPointHistory)
    }

    @Transactional
    fun sendPoint(sendPointRequest: SendPointRequest, userPrincipal: UserPrincipal) {
        // 차감
        val findMember = socialMemberRepository.findByIdOrNull(userPrincipal.memberId) ?: throw RestApiException(ErrorCode.MODEL_NOT_FOUND)

        findMember.verifyPoint()

        val findPointHistory = sendPointRequest.to(findMember)

        findMember.updatePoint(findPointHistory.change)

        pointHistoryRepository.save(findPointHistory)

        // 증액
        val receivedMember = socialMemberRepository.findByEmail(sendPointRequest.memberEmail) ?: throw RestApiException(ErrorCode.MODEL_NOT_FOUND)
        val receivedPointHistory = sendPointRequest.toReceivedMember(receivedMember)

        receivedMember.receivePoint(sendPointRequest.sendPoint)

        pointHistoryRepository.save(receivedPointHistory)
    }

    fun userFindPointHistory(userPrincipal: UserPrincipal): List<PointHistoryRowDto> {
        return pointHistoryRepository.userFindPointHistory(userPrincipal.memberId)
    }
}