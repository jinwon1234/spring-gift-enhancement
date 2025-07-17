package gift.wishproduct.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishProduct;
import gift.global.exception.BadRequestEntityException;
import gift.global.exception.NotFoundEntityException;
import gift.member.service.MemberService;
import gift.product.service.ProductService;
import gift.wishproduct.dto.WishProductCreateReq;
import gift.wishproduct.dto.WishProductResponse;
import gift.wishproduct.dto.WishProductUpdateReq;
import gift.wishproduct.repository.WishProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class WishProductServiceV1 implements WishProductService {

    private final WishProductRepository wishProductRepository;
    private final ProductService productService;
    private final MemberService memberService;

    public WishProductServiceV1(WishProductRepository wishProductRepository, ProductService productService, MemberService memberService, EntityManager em) {
        this.wishProductRepository = wishProductRepository;
        this.productService = productService;
        this.memberService = memberService;
    }


    @Override
    public Long save(WishProductCreateReq dto, String email) {

        Product product = productService.findById(dto.getProductId());

        Member owner = memberService.findByEmail(email);

        WishProduct wishProduct = wishProductRepository.findByOwnerIdAndProductId(owner.getId(), product.getId())
                .orElse(null);

        if (wishProduct == null) {
            WishProduct saved = wishProductRepository.save(new WishProduct(dto.getQuantity(), owner, product));

            return saved.getId();
        }

        wishProduct.changeQuantity(dto.getQuantity() + wishProduct.getQuantity());
        return wishProduct.getId();
    }

    @Override
    public List<WishProductResponse> findByEmail(String email) {

        Member owner = memberService.findByEmail(email);

        return wishProductRepository.findWithProductByOwnerId(owner.getId())
                .stream().map(WishProductResponse::new)
                .toList();
    }

    @Override
    public Page<WishProductResponse> findByEmailWithPage(String email, Pageable pageable) {
        Member owner = memberService.findByEmail(email);

        return wishProductRepository.findWithProductByOwnerIdWithPage(owner.getId(), pageable)
                .map(WishProductResponse::new);
    }

    @Override
    public void deleteById(Long id, String email) {

        WishProduct wishProduct = wishProductRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("존재하지 않는 위시 상품입니다."));

        Member owner = memberService.findByEmail(email);

        if (!owner.getId().equals(wishProduct.getOwner().getId()))
            throw new BadRequestEntityException("자신의 위시 상품만 삭제할 수 있습니다");

        wishProductRepository.deleteById(wishProduct.getId());
    }

    @Override
    public void updateQuantity(Long id, WishProductUpdateReq dto, String email) {

        WishProduct wishProduct = wishProductRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("존재하지 않는 위시 상품입니다."));

        Member owner = memberService.findByEmail(email);

        if (!owner.getId().equals(wishProduct.getOwner().getId()))
            throw new BadRequestEntityException("자신의 위시 상품만 수정할 수 있습니다");

        wishProduct.changeQuantity(dto.getQuantity());

    }


}
