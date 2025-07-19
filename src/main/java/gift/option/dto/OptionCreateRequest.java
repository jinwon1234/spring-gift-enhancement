package gift.option.dto;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import static gift.util.PatternUtil.*;

public record OptionCreateRequest(
        @Pattern(regexp = OPTION_NAME_PATTERN) String optionName,
        @Range(min = 1, max = 99999999) int quantity) {
}
