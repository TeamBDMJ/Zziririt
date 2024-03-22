package kr.zziririt.zziririt.api.pointevent.controller

import kr.zziririt.zziririt.api.pointevent.dto.request.CreatePointEventRequest
import kr.zziririt.zziririt.api.pointevent.dto.request.UpdatePointEventRequest
import kr.zziririt.zziririt.api.pointevent.service.PointEventService
import kr.zziririt.zziririt.global.responseEntity
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/pointevent")
class PointEventController(
    private val pointEventService: PointEventService
) {

    @PostMapping("/firstcome")
    fun createPointEvent(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: CreatePointEventRequest
    ) = responseEntity(HttpStatus.OK) { pointEventService.createPointEvent(request, userPrincipal) }

    @PutMapping("/firstcome/{pointEventId}")
    fun updatePointEvent(
        @PathVariable pointEventId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: UpdatePointEventRequest
    ) = responseEntity(HttpStatus.OK) { pointEventService.updatePointEventStatus(pointEventId, request, userPrincipal) }

    @PostMapping("/firstcome/{pointEventId}")
    fun participateEvent(@PathVariable pointEventId: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal) = responseEntity(HttpStatus.CREATED) { pointEventService.participateEvent(pointEventId, userPrincipal) }

    @DeleteMapping("/firstcome/{pointEventId}")
    fun deletePointEvent(@PathVariable pointEventId: Long, @AuthenticationPrincipal userPrincipal: UserPrincipal) = responseEntity(HttpStatus.NO_CONTENT) { pointEventService.deletePointEvent(pointEventId, userPrincipal) }
}