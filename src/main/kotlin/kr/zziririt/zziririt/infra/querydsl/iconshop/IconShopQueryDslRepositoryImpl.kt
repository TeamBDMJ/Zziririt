package kr.zziririt.zziririt.infra.querydsl.iconshop

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import kr.zziririt.zziririt.api.iconshop.dto.IconSearchCondition
import kr.zziririt.zziririt.domain.iconshop.model.QIconShopEntity
import kr.zziririt.zziririt.domain.iconshop.model.SaleStatus
import kr.zziririt.zziririt.infra.querydsl.QueryDslSupport
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.IconShopRowDto
import kr.zziririt.zziririt.infra.querydsl.iconshop.dto.QIconShopRowDto
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class IconShopQueryDslRepositoryImpl : IconShopQueryDslRepository, QueryDslSupport() {

    private val iconShop = QIconShopEntity.iconShopEntity

    override fun searchBy(condition: IconSearchCondition, pageable: Pageable): PageImpl<IconShopRowDto> {
        val statusCondition = iconShop.saleStatus.`in`(
            SaleStatus.PREPARE, SaleStatus.SALE, SaleStatus.SOLDOUT
        )

        val result = queryFactory
            .select(
                QIconShopRowDto(
                    iconShop.icon.id,
                    iconShop.price,
                    iconShop.iconQuantity,
                    iconShop.saleStatus
                )
            )
            .from(iconShop)
            .where(
                saleStatusEq(condition.searchType)
            )
            .orderBy(*createOrderSpecifiers(pageable))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count: Long = queryFactory
            .select(iconShop.count())
            .from(iconShop)
            .where(statusCondition)
            .fetchOne() ?: 0L

        return PageImpl(result, pageable, count)
    }

    private fun saleStatusEq(saleStatus: String?): BooleanExpression? {
        if (saleStatus.isNullOrEmpty()) {
            return null
        }
        return when (saleStatus) {
            SaleStatus.PREPARE.name -> iconShop.saleStatus.eq(SaleStatus.PREPARE)
            SaleStatus.SALE.name -> iconShop.saleStatus.eq(SaleStatus.SALE)
            SaleStatus.SOLDOUT.name -> iconShop.saleStatus.eq(SaleStatus.SOLDOUT)
            else -> null
        }
    }

    private fun createOrderSpecifiers(pageable: Pageable): Array<OrderSpecifier<*>?> {
        return pageable.sort?.let { sort ->
            sort.mapNotNull { order ->
                val direction = if (order.isAscending) Order.ASC else Order.DESC
                when (order.property) {
                    "price" -> OrderSpecifier(direction, QIconShopEntity.iconShopEntity.price)
                    "iconQuantity" -> OrderSpecifier(direction, QIconShopEntity.iconShopEntity.iconQuantity)
                    else -> OrderSpecifier(direction, QIconShopEntity.iconShopEntity.createdAt)
                }
            }.toTypedArray()
        } ?: arrayOf()
    }
}