package gift.product.dto;

import gift.domain.Product;

import java.util.UUID;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageURL;
    private Long memberId;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageURL = product.getImageUrl();
        this.memberId = product.getMember().getId();
    }

    protected ProductResponse() {}
  
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Long getMemberId() {
        return memberId;
    }
}
