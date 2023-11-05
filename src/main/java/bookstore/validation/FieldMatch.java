package bookstore.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FiledMatcherValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface FieldMatch {
    String[] fields();

    String message() default "Fields are not matched";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
