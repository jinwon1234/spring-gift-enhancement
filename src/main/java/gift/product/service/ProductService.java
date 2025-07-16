package gift.product.service;


import gift.domain.Product;
import gift.member.dto.AuthMember;
import gift.product.dto.ProductCreateRequest;
import gift.product.dto.ProductResponse;
import gift.product.dto.ProductUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Long save(ProductCreateRequest dto, String email);
    List<ProductResponse> findAllProducts();
    ProductResponse findProduct(Long id);
    void deleteProduct(Long id, AuthMember authMember);
    void updateProduct(Long id, ProductUpdateRequest dto, AuthMember authMember);
    List<ProductResponse> findByEmail(AuthMember authMember);
    Product findById(Long id);
}
