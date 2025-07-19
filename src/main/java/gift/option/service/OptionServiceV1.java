package gift.option.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.option.dto.OptionCreateRequest;
import gift.option.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OptionServiceV1 implements OptionService{

    private final OptionRepository optionRepository;

    public OptionServiceV1(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public void save(List<OptionCreateRequest> options, Product product) {

        options.forEach(option -> {
                    optionRepository.save(new Option(option.optionName(), option.quantity(), product));
                });
    }
}
