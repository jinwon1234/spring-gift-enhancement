package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UniqueOption",
                        columnNames = {"name", "product_id"})
        }
)
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    protected Option() {}

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option(Long id, String name, int quantity, Product product) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
