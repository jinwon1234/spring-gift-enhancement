package gift.option.service;

import gift.domain.Product;
import gift.option.dto.OptionCreateRequest;

import java.util.List;

public interface OptionService {

    void save(List<OptionCreateRequest> options, Product product);
}
