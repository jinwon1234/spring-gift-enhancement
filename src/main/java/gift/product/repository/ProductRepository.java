package gift.product.repository;

import gift.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMemberId(Long memberId);

    @Query("select p from Product p where p.member.id = :memberId")
    Page<Product> findByMemberIdWithPage(Long memberId, Pageable pageable);

    @Query("select p from Product p")
    Page<Product> findAllWithPage(Pageable pageable);
}
