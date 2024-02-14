package hello.itemservice.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    // MessageCodesResolver 생성 규칙
    // 객체 오류,
    // 1. code + "." + object name / 2. code
    // 필드 오류,
    // 1. code + "." + object name + "." + field / 2. code + "." + field
    // 3. code + "." + field type / 4. code
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes =
                codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes)
                .containsExactly(
                        "required.item.itemName",
                        "required.itemName",
                        "required.java.lang.String",
                        "required");
    }
}
