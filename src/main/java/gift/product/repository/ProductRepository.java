package gift.product.repository;

import gift.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMemberId(Long memberId);
}
