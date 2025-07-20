package gift.option.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.global.exception.NotFoundEntityException;
import gift.member.dto.AuthMember;
import gift.member.service.MemberService;
import gift.option.dto.OptionCreateRequest;
import gift.option.dto.OptionResponse;
import gift.option.dto.OptionUpdateRequest;
import gift.option.repository.OptionRepository;
import gift.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OptionServiceV1 implements OptionService{

    private final OptionRepository optionRepository;
    private final MemberService memberService;

    public OptionServiceV1(OptionRepository optionRepository, MemberService memberService) {
        this.optionRepository = optionRepository;
        this.memberService = memberService;
    }

    @Override
    public void save(List<OptionCreateRequest> options, Product product) {

        if (options.isEmpty()) return;

        options.forEach(option -> {
            Option save = optionRepository.save(new Option(option.optionName(), option.quantity(), product));
            product.getOptions().add(save);
        });
    }

    @Override
    public void deleteById(AuthMember authMember, Long id) {

        validateIsOwner(authMember, id);

        optionRepository.deleteById(id);

    }

    @Override
    public OptionResponse findById(AuthMember authMember, Long id) {

        Option option = validateIsOwner(authMember, id);

        return new OptionResponse(option.getId(), option.getName(), option.getQuantity());
    }

    @Override
    public List<OptionResponse> findByProduct(Product product) {

        return optionRepository.findByProductId(product.getId())
                .stream().map(option -> new OptionResponse(option.getId(), option.getName(), option.getQuantity()))
                .toList();
    }

    @Override
    public void changeQuantity(AuthMember authMember, Long id, OptionUpdateRequest optionUpdateRequest) {

        Option option = validateIsOwner(authMember, id);

        option.changeQuantity(optionUpdateRequest.quantity());

    }

    private Option validateIsOwner(AuthMember authMember, Long id) {
        Option option = optionRepository.findByIdWithProduct(id)
                .orElseThrow(() -> new NotFoundEntityException("존재하는 옵션이 아닙니다."));

        memberService.isOwnerOrAdmin(authMember.getEmail(), option.getProduct().getMember().getId());

        return option;
    }
}
