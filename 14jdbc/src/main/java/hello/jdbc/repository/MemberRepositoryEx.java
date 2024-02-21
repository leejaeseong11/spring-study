package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException; // 구현체가 던지는 체크 예외 명시 필요

    Member findById(String memberId) throws SQLException;

    void update(String memberID, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
