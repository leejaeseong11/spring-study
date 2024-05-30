package study.querydsl.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String name;
    private int age;

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
