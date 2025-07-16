package gift.wishproduct.service;

import gift.wishproduct.dto.WishProductCreateReq;
import gift.wishproduct.dto.WishProductResponse;
import gift.wishproduct.dto.WishProductUpdateReq;

import java.util.List;
import java.util.UUID;

public interface WishProductService {

    Long save(WishProductCreateReq wishProductCreateReq, String email);

    List<WishProductResponse> findByEmail(String email);

    void deleteById(Long id, String email);

    void updateQuantity(Long id, WishProductUpdateReq wishProductUpdateReq, String email);
}
