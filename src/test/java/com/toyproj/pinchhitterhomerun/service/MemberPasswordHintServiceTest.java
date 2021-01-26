package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberPasswordHintServiceTest {

    @Autowired
    MemberPasswordHintService memberPasswordHintService;

    TestAccountManager testAccountManager;
    private boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager = new TestAccountManager();
            initialized = true;
        }
    }

    @Test
    public void 아이디와_생년월일로_비밀번호_힌트_가져오기() {
        // given
        final var loginId = testAccountManager.testMember.getLoginId();
        final var birthDay = testAccountManager.testMember.getBirthDay();

        // when
        final var result = memberPasswordHintService.getPasswordHint(loginId, birthDay);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getText()).isEqualTo("none");
    }

    @Test
    public void 힌트_답변_매핑_성공() {
        // given
        final var loginId = testAccountManager.testMember.getLoginId();
        final var birthDay = testAccountManager.testMember.getBirthDay();
        final String testAnswer = "답변";

        // when
        final var result = memberPasswordHintService.matchHintAnswer(loginId, birthDay, testAnswer);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
    }

    @Test
    public void 힌트_답변_매핑_실패() {
        // given
        final var loginId = testAccountManager.testMember.getLoginId();
        final var birthDay = testAccountManager.testMember.getBirthDay();
        final String testAnswer = "wrongAnswer";

        // when
        final var result = memberPasswordHintService.matchHintAnswer(loginId, birthDay, testAnswer);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.PASSWORD_HINT_NOT_MATCHED.getMessage());
    }
}
