package gift.product.service;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Role;
import gift.global.exception.BadRequestEntityException;
import gift.global.exception.NotFoundEntityException;
import gift.member.dto.AuthMember;
import gift.member.service.MemberService;
import gift.product.dto.ProductCreateRequest;
import gift.product.dto.ProductResponse;
import gift.product.dto.ProductUpdateRequest;
import gift.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class ProductServiceV1 implements ProductService{

    private final ProductRepository productRepository;
    private final MemberService memberService;

    public ProductServiceV1(ProductRepository productRepository, MemberService memberService) {
        this.productRepository = productRepository;
        this.memberService = memberService;
    }


    public Long save(ProductCreateRequest dto, String email) {
        Member findMember = memberService.findByEmail(email);
        Product save = productRepository.save(new Product(dto.getName(), dto.getPrice(), dto.getImageURL(), findMember));
        return save.getId();
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream().map(ProductResponse::new)
                .toList();
    }

    @Override
    public Page<ProductResponse> findAllProductsWithPage(Pageable pageable) {
        return productRepository.findAllWithPage(pageable)
                .map(ProductResponse::new);
    }


    public ProductResponse findProduct(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("상품이 존재하지 않습니다."));
        return new ProductResponse(findProduct);
    }

    public void deleteProduct(Long id, AuthMember authMember) {

        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("상품이 존재하지 않습니다."));

        Member findMember = memberService.findByEmail(authMember.getEmail());

        checkIsAdminOrOwner(authMember,findMember, findProduct.getMember().getId());

        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductUpdateRequest dto, AuthMember authMember) {


        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("상품이 존재하지 않습니다."));

        Member findMember = memberService.findByEmail(authMember.getEmail());
        checkIsAdminOrOwner(authMember, findMember, findProduct.getMember().getId());

        findProduct.changeName(dto.getName());
        findProduct.changePrice(dto.getPrice());
        findProduct.changeImageUrl(dto.getImageURL());
    }

    @Override
    public List<ProductResponse> findByEmail(AuthMember authMember) {
        Member findMember = memberService.findByEmail(authMember.getEmail());

       return productRepository.findByMemberId(findMember.getId())
                .stream().map(ProductResponse::new).toList();
    }

    @Override
    public Page<ProductResponse> findByEmailWithPage(AuthMember authMember, Pageable pageable) {
        Member findMember = memberService.findByEmail(authMember.getEmail());

        return productRepository.findByMemberIdWithPage(findMember.getId(), pageable)
                .map(ProductResponse::new);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()->new NotFoundEntityException("존재하는 상품이 아닙니다"));
    }

    private void checkIsAdminOrOwner(AuthMember authMember, Member member,  Long ownerId) {

        if (authMember.getRole() == Role.ADMIN) return;

        if (!member.getId().equals(ownerId))
            throw new BadRequestEntityException("자신의 상품이 아닙니다.");
    }
}
