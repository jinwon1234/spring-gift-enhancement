package gift.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishProduct> wishProducts;

    protected Product() {}

    public Product(String name, int price, String imageUrl, Member member) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.member = member;
    }

    public Product(UUID id, String name, int price, String imageUrl, Member member) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.member = member;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Member getMember() {
        return member;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
