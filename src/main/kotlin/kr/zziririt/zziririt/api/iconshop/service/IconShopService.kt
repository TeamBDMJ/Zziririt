package kr.zziririt.zziririt.api.iconshop.service

import jakarta.transaction.Transactional
import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.api.iconshop.dto.request.BuyInIconShopRequest
import kr.zziririt.zziririt.api.iconshop.dto.request.ChangeSaleStatusRequest
import kr.zziririt.zziririt.api.iconshop.dto.request.IconRegistrationRequest
import kr.zziririt.zziririt.api.iconshop.dto.response.IconShopResponse
import kr.zziririt.zziririt.domain.icon.repository.IconRepository
import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus
import kr.zziririt.zziririt.domain.iconshop.repository.IconShopRepository
import kr.zziririt.zziririt.domain.member.model.MemberIconEntity
import kr.zziririt.zziririt.domain.member.repository.MemberIconRepository
import kr.zziririt.zziririt.domain.member.repository.SocialMemberRepository
import kr.zziririt.zziririt.global.exception.ErrorCode
import kr.zziririt.zziririt.global.exception.ModelNotFoundException
import kr.zziririt.zziririt.global.exception.RestApiException
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.IconShopRowDto
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class IconShopService(
    private val iconShopRepository: IconShopRepository,
    private val iconRepository: IconRepository,
    private val memberRepository: SocialMemberRepository,
    private val memberIconRepository: MemberIconRepository,
) {
    fun iconShopRegistration(request: IconRegistrationRequest) {
        val iconCheck =
            iconRepository.findByIdOrNull(request.iconId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        check(iconShopRepository.findByIdOrNull(request.iconId) == null) {
            throw RestApiException(ErrorCode.DUPLICATE_ICON_ID)
        }

        val iconShop = request.toEntity(iconCheck)

        iconShopRepository.save(iconShop)
    }

    @Transactional
    fun changeStatus(iconShopId: Long, request: ChangeSaleStatusRequest) {
        val iconShopCheck =
            iconShopRepository.findByIdOrNull(iconShopId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        iconShopCheck.changeStatus(request.saleStatus)
    }

    @Transactional
    fun iconShopDelete(iconShopId: Long) {
        val iconShopCheck =
            iconShopRepository.findByIdOrNull(iconShopId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        iconShopRepository.delete(iconShopCheck)
    }

    @Transactional
    fun getIconShop(iconShopId: Long): IconShopResponse {
        val iconShopCheck =
            iconShopRepository.findByIdOrNull(iconShopId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        return IconShopResponse.from(iconShopCheck)
    }

    fun getIconShops(condition: IconSearchCondition, pageable: Pageable): PageImpl<IconShopRowDto> {

        return iconShopRepository.searchBy(
            condition,
            pageable
        )
    }

    @Transactional
    fun buyInIconShop(request: BuyInIconShopRequest, userPrincipal: UserPrincipal) {
        val memberCheck = memberRepository.findByIdOrNull(userPrincipal.memberId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)
        val iconShopCheck = iconShopRepository.findByIdOrNull(request.iconShopId)
            ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)
        val memberIconCheck = memberIconRepository.findByIdOrNull(memberCheck.id!!)

        check(memberCheck.point > iconShopCheck.price) {
            throw RestApiException(ErrorCode.NOT_ENOUGH_POINT)
        }
        check(iconShopCheck.iconQuantity > 0) {
            throw RestApiException(ErrorCode.NOT_ENOUGH_ICONQUANTITY)
        }
        check(iconShopCheck.saleStatus == SaleStatus.SALE) {
            throw RestApiException(ErrorCode.NOT_FOR_SALE_STATUS)
        }
        check(memberIconCheck?.icon?.id != iconShopCheck.icon.id) {
            throw RestApiException(ErrorCode.ALREADY_HAVE_ICON)
        }

        iconShopCheck.reduceIconQuantityAndChangeSaleStatus()
        memberCheck.pointMinus(iconShopCheck.price)
        memberIconRepository.save(MemberIconEntity(memberCheck, iconShopCheck.icon))
    }
}