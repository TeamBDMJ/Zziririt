package kr.zziririt.zziririt.api.pointevent.dto.request

import kr.zziririt.zziririt.api.pointevent.dto.EventStatus

data class UpdatePointEventRequest(
    val status: EventStatus
)