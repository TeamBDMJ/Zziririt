package kr.zziririt.zziririt.api.pointevent.dto.request

import kr.zziririt.zziririt.api.pointevent.dto.EventStatus
import kr.zziririt.zziririt.domain.pointevent.model.PointEventEntity
import java.time.LocalDateTime

data class CreatePointEventRequest(
    val title: String,
    val content: String,
    val admissionPoint: Long,
    val limitMember: Int,
    val period: Int
){
    fun toEntity(startDate: LocalDateTime) : PointEventEntity = PointEventEntity(
        title = title,
        content = content,
        limitMember = limitMember,
        admissionPoint = admissionPoint,
        nowMember = 0,
        startDate = startDate,
        eventStatus = EventStatus.PREPARE,
    )
}
