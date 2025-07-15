package gift.member.repository;

import gift.domain.Member;
import gift.domain.Role;
import gift.global.exception.NotFoundEntityException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("화원 저장 성공")
    void save() {

        // given
        Member member = new Member("ljw0626@naver.com", "Qwer1234!!", Role.REGULAR);

        // when
        Member save = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId())
                .get();

        // then
        assertThat(findMember.getId()).isEqualTo(save.getId());
        assertThat(findMember.getEmail()).isEqualTo(save.getEmail());
    }

    @Test
    @DisplayName("이메일로 회원 조회")
    void findByEmail() {
        // given
        Member member = new Member("ljw0626@naver.com", "Qwer1234!!", Role.REGULAR);

        // when
        Member save = memberRepository.save(member);

        Member findMember = memberRepository.findByEmail(member.getEmail())
                .get();

        // then
        assertThat(findMember.getId()).isEqualTo(save.getId());
        assertThat(findMember.getEmail()).isEqualTo(save.getEmail());
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteById() {
        // given
        Member member = new Member("ljw0626@naver.com", "Qwer1234!!", Role.REGULAR);

        // when
        Member save = memberRepository.save(member);
        memberRepository.deleteById(save.getId());
        Optional<Member> findMember = memberRepository.findById(save.getId());

        // then
        assertThat(findMember).isNotPresent();
    }

}