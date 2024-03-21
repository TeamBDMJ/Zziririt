package kr.zziririt.zziririt.api.point.controller

import kr.zziririt.zziririt.api.dto.CommonResponse
import kr.zziririt.zziririt.api.point.dto.request.BuyIconRequest
import kr.zziririt.zziririt.api.point.dto.request.SendPointRequest
import kr.zziririt.zziririt.api.point.service.PointHistoryService
import kr.zziririt.zziririt.global.responseEntity
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/points/history")
class PointHistoryController(
    private val pointHistoryService: PointHistoryService
) {
    @PostMapping("/buy")
    fun buyIcon(
        @RequestBody buyIconRequest: BuyIconRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommonResponse<Nothing>> {
        pointHistoryService.buyIcon(buyIconRequest, userPrincipal)
        return responseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/send")
    fun sendPoint(
        @RequestBody sendPointRequest: SendPointRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<CommonResponse<Nothing>> {
        pointHistoryService.sendPoint(sendPointRequest, userPrincipal)
        return responseEntity(HttpStatus.CREATED)
    }

    @GetMapping
    fun getPointHistory(@AuthenticationPrincipal userPrincipal: UserPrincipal) =
        responseEntity(HttpStatus.OK) { pointHistoryService.userFindPointHistory(userPrincipal) }
}
