package gift.wishproduct.repository;

import gift.domain.WishProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishProductRepository extends JpaRepository<WishProduct, UUID> {


    @Query("select w from WishProduct w where w.product.id = :productId and w.owner.id = :ownerId")
    Optional<WishProduct> findByOwnerIdAndProductId(UUID ownerId, UUID productId);


    @Query("select w from WishProduct w join fetch w.product where w.owner.id = :ownerId")
    List<WishProduct> findWithProductByOwnerId(UUID ownerId);
}
