package kr.zziririt.zziririt.api.iconshop.controller

import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.api.iconshop.dto.request.BuyInIconShopRequest
import kr.zziririt.zziririt.api.iconshop.dto.request.ChangeSaleStatusRequest
import kr.zziririt.zziririt.api.iconshop.dto.request.IconRegistrationRequest
import kr.zziririt.zziririt.api.iconshop.service.IconShopService
import kr.zziririt.zziririt.global.responseEntity
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/iconshop")
class IconShopController(
    private val iconShopService: IconShopService,
) {

    @PostMapping("/registration")
    fun iconShopRegistration(
        @RequestBody request: IconRegistrationRequest
    ) = responseEntity(HttpStatus.OK) { iconShopService.iconShopRegistration(request) }


    @PutMapping("/{iconShopId}")
    fun changeSaleStatus(
        @PathVariable iconShopId: Long,
        @RequestBody request: ChangeSaleStatusRequest
    ) = responseEntity(HttpStatus.OK) { iconShopService.changeStatus(iconShopId, request) }

    @DeleteMapping("/{iconShopId}")
    fun iconShopDelete(
        @PathVariable iconShopId: Long,
    ) = responseEntity(HttpStatus.OK) { iconShopService.iconShopDelete(iconShopId) }

    @GetMapping("/{iconShopId}")
    fun getIconShop(
        @PathVariable iconShopId: Long
    ) = responseEntity(HttpStatus.OK) { iconShopService.getIconShop(iconShopId) }

    @GetMapping()
    fun getIconShops(
        condition: IconSearchCondition,
        @PageableDefault(
            size = 30,
            sort = ["createdAt"]
        ) pageable: Pageable
    ) = responseEntity(HttpStatus.OK) { iconShopService.getIconShops(condition, pageable)}

    @PostMapping("/purchase")
    fun buyInIconShop(
        @RequestBody request: BuyInIconShopRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { iconShopService.buyInIconShop(request, userPrincipal)}
}