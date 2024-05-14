package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import study.datajpa.entity.Item;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;

    @Test
    public void save() {
        // 식별자가 null이면 persist, 그렇지 않으면 merge가 발생함
        // merge는 select 쿼리가 무조건 한 번 나가기 때문에 좋지 않음
        //        Item item = new Item();
        Item item = new Item("A");
        itemRepository.save(item);
    }
}
