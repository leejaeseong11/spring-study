package hello.itemservice.domain.item;

import lombok.Data;

@Data
// ObjectError 사용 방법 중 하나이지만 JDK 14 이후에는 사용 불가능
// @ScriptAssert(
//        lang = "javascript",
//        script = "_this.price * _this.quantity >= 10000",
//        message = "총합이 10000원 넘게 입력해주세요.")
public class Item {
    //    @NotNull(groups = UpdateCheck.class) // 수정 요구사항 추가
    private Long id;

    //    @NotBlank(message = "공백X") // 기본 메시지
    //    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //    @Range(
    //            min = 1000,
    //            max = 1000000,
    //            groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    //    @Max(
    //            value = 9999,
    //            groups = {SaveCheck.class})
    private Integer quantity;

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
