package study.querydsl.repository;

import static org.springframework.util.StringUtils.hasText;

import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory
                .select(
                        new QMemberTeamDto(
                                member.id.as("memberId"),
                                member.username,
                                member.age,
                                team.id.as("teamId"),
                                team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(
            MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results =
                queryFactory
                        .select(
                                new QMemberTeamDto(
                                        member.id.as("memberId"),
                                        member.username,
                                        member.age,
                                        team.id.as("teamId"),
                                        team.name.as("teamName")))
                        .from(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 데이터 내용과 전체 카운트를 별도로 조회
    @Override
    public Page<MemberTeamDto> searchPageComplex(
            MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content =
                queryFactory
                        .select(
                                new QMemberTeamDto(
                                        member.id.as("memberId"),
                                        member.username,
                                        member.age,
                                        team.id.as("teamId"),
                                        team.name.as("teamName")))
                        .from(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        //        long total =
        //                queryFactory
        //                        .selectFrom(member)
        //                        .leftJoin(member.team, team)
        //                        .where(
        //                                usernameEq(condition.getUsername()),
        //                                teamNameEq(condition.getTeamName()),
        //                                ageGoe(condition.getAgeGoe()),
        //                                ageLoe(condition.getAgeLoe()))
        //                        .fetchCount();

        JPAQuery<Member> countQuery =
                queryFactory
                        .selectFrom(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe()));

        //        return new PageImpl<>(content, pageable, total);
        // 카운트 쿼리 최적화(데이터가 페이지 사이즈보다 작은 경우 카운트 쿼리 생략)
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}
