package kr.zziririt.zziririt.api.pointproduct.service

import io.kotest.matchers.shouldBe
import kr.zziririt.zziririt.api.board.service.BoardService
import kr.zziririt.zziririt.api.iconproduct.dto.request.BuyIconProductRequest
import kr.zziririt.zziririt.api.iconproduct.service.IconProductService
import kr.zziririt.zziririt.domain.icon.model.IconEntity
import kr.zziririt.zziririt.domain.icon.repository.IconRepository
import kr.zziririt.zziririt.domain.iconproduct.model.IconProductEntity
import kr.zziririt.zziririt.domain.iconproduct.model.SaleStatus
import kr.zziririt.zziririt.domain.iconproduct.repository.IconProductRepository
import kr.zziririt.zziririt.domain.member.repository.MemberIconRepository
import kr.zziririt.zziririt.domain.member.repository.SocialMemberRepository
import kr.zziririt.zziririt.infra.aws.S3Service
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class IconProductServiceTest {

    @Autowired
    private lateinit var socialMemberRepository: SocialMemberRepository
    @Autowired
    private lateinit var s3Service: S3Service
    @Autowired
    private lateinit var boardService: BoardService
    @Autowired
    private lateinit var iconProductRepository: IconProductRepository
    @Autowired
    private lateinit var iconRepository: IconRepository
    @Autowired
    private lateinit var memberRepository: SocialMemberRepository
    @Autowired
    private lateinit var memberIconRepository: MemberIconRepository

    @Autowired
    private lateinit var iconProductService: IconProductService

    @Test
    fun `buy icon product successfully`() {
        val icon = IconEntity("icon", "url", "url", "maker")
        val iconProduct = IconProductEntity(icon = icon, price = 100, iconQuantity = 10, SaleStatus.SALE)

        val userPrincipal = UserPrincipal(memberId = 2, email = "email", roles = setOf("ROLE_USER"), providerId = "testProvider")

        val request = BuyIconProductRequest(iconProductId = iconProduct.id)

        iconProductService.buyIconProduct(request, userPrincipal)

        iconProduct.iconQuantity shouldBe 9
    }
}
