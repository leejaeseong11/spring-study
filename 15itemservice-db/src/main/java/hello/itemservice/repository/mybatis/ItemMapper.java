package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {
    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    // xml에 정의하는 대신 @Select 같은 것을 쓸 수도 있음
    // '$'를 넣어 문자 자체를 쿼리에 넣을 수 있음
    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);
}
