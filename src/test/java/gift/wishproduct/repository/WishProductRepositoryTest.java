package gift.wishproduct.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Role;
import gift.domain.WishProduct;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class WishProductRepositoryTest {

    @Autowired
    private WishProductRepository wishProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("위시 상품 저장 & 조회")
    void save() {
        // given
        Member member = new Member("ljw2109@naver.com", "Qwer1234!!", Role.REGULAR);
        Product product = new Product("스윙칩", 3000, "image", member);
        memberRepository.save(member);
        productRepository.save(product);
        WishProduct saved = wishProductRepository.save(new WishProduct(30, member, product));

        // when
        WishProduct findWishProduct = wishProductRepository.findById(saved.getId()).get();

        // then
        assertThat(saved.getId()).isEqualTo(findWishProduct.getId());
        assertThat(findWishProduct.getOwner().getId()).isEqualTo(member.getId());
        assertThat(findWishProduct.getProduct().getId()).isEqualTo(product.getId());
        assertThat(findWishProduct.getQuantity()).isEqualTo(saved.getQuantity());
    }

    @Test
    @DisplayName("ownerId & productId로 조회")
    void findByOwnerIdAndProductId() {

        // given
        Member member = new Member("ljw2109@naver.com", "Qwer1234!!", Role.REGULAR);
        Product product = new Product("스윙칩", 3000, "image", member);
        memberRepository.save(member);
        productRepository.save(product);
        WishProduct saved = wishProductRepository.save(new WishProduct(30, member, product));

        // when
        WishProduct findWishProduct = wishProductRepository.findByOwnerIdAndProductId(member.getId(), product.getId()).get();

        // then
        assertThat(saved.getId()).isEqualTo(findWishProduct.getId());
        assertThat(findWishProduct.getOwner().getId()).isEqualTo(member.getId());
        assertThat(findWishProduct.getProduct().getId()).isEqualTo(product.getId());
        assertThat(findWishProduct.getQuantity()).isEqualTo(saved.getQuantity());

    }

    @Test
    @DisplayName("자신의 위시리스트 조회")
    void findWithProductByOwnerId() {
        // given
        Member member = new Member("ljw2109@naver.com", "Qwer1234!!", Role.REGULAR);
        Product product = new Product("스윙칩", 3000, "image", member);
        memberRepository.save(member);
        productRepository.save(product);
        WishProduct saved = wishProductRepository.save(new WishProduct(30, member, product));

        // when
        List<WishProduct> result = wishProductRepository.findWithProductByOwnerId(member.getId());

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("위시 상품 삭제")
    void deleteById() {
        // given
        Member member = new Member("ljw2109@naver.com", "Qwer1234!!", Role.REGULAR);
        Product product = new Product("스윙칩", 3000, "image", member);
        memberRepository.save(member);
        productRepository.save(product);
        WishProduct saved = wishProductRepository.save(new WishProduct(30, member, product));

        // when
        wishProductRepository.deleteById(saved.getId());
        Optional<WishProduct> findWishProduct = wishProductRepository.findById(saved.getId());

        // then
        assertThat(findWishProduct).isNotPresent();
    }
}