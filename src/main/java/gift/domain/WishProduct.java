package gift.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "wish_product")
public class WishProduct {

    @Id
    private UUID id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public WishProduct(UUID id, int quantity, Member owner, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.owner = owner;
        this.product = product;
    }

    public WishProduct(int quantity, Member owner, Product product) {
        this.id = UUID.randomUUID();
        this.quantity = quantity;
        this.owner = owner;
        this.product = product;
    }

    protected WishProduct() {}

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Member getOwner() {
        return owner;
    }

    public Product getProduct() {
        return product;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
