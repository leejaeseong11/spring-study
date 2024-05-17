package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    @Value("#{target.username + ' ' + target.age}") // 엔티티를 모두 가져와서 처리해서 open projection 이라고 함
    String getUsername(); // 그냥 엔티티를 최적화해서 가져오는 건 close projection 이라고 함
}
