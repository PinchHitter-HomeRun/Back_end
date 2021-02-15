package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberPasswordHintServiceTest extends TestHelper {

    @Autowired
    MemberPasswordHintService memberPasswordHintService;

    @Autowired
    TestAccountManager testAccountManager;

    static boolean initialized = false;

    @BeforeEach
    public void clean() {
        if (!initialized) {
            testAccountManager.process();
            initialized = true;
        }
    }

    @Test
    public void 아이디와_생년월일로_비밀번호_힌트_가져오기() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var birthDay = TestAccountManager.testMember.getBirthDay();

        // when
        final var result = memberPasswordHintService.getPasswordHint(loginId, birthDay);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getHintText()).isEqualTo("none");
    }

    @Test
    public void 힌트_답변_매핑_성공() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var birthDay = TestAccountManager.testMember.getBirthDay();
        final String testAnswer = "답변";
        final var memberHint = memberPasswordHintService.getPasswordHint(loginId, birthDay);
        assertThat(memberHint.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = memberPasswordHintService.matchHintAnswer(memberHint.getResponse().getId(), testAnswer);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse()).isEqualTo(true);
    }

    @Test
    public void 힌트_답변_매핑_실패() {
        // given
        final var loginId = TestAccountManager.testMember.getLoginId();
        final var birthDay = TestAccountManager.testMember.getBirthDay();
        final String testAnswer = "wrongAnswer";
        final var memberHint = memberPasswordHintService.getPasswordHint(loginId, birthDay);
        assertThat(memberHint.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = memberPasswordHintService.matchHintAnswer(memberHint.getResponse().getId(), testAnswer);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.PASSWORD_HINT_NOT_MATCHED.getMessage());
    }
}
