package gift.product.dto;

import gift.domain.Product;

import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String name;
    private int price;
    private String imageUrl;
    private UUID memberId;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.memberId = product.getMember().getId();
    }

    protected ProductResponse() {}
  
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

    public UUID getMemberId() {
        return memberId;
    }
}
