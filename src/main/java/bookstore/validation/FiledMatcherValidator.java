package bookstore.validation;

import bookstore.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FiledMatcherValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (requestDto != null) {
            return requestDto.password().equals(requestDto.repeatPassword());
        }
        return false;
    }
}
