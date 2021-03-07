package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.helper.TestAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestAdminAccountManager;
import com.toyproj.pinchhitterhomerun.helper.TestHelper;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationServiceTest extends TestHelper {

    @Autowired
    NotificationService notificationService;

    @Autowired
    TestAdminAccountManager testAdminAccountManager;

    @Autowired
    TestAccountManager testAccountManager;

    @BeforeAll
    public void init() {
        testAdminAccountManager.process();
    }

    @AfterEach
    public void deleteAllNotification() {
        testAdminAccountManager.deleteAllNotification();
    }

    @Test
    public void 일반_공지사항_작성() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        // when
        final var result = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getAllNotification();
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = afterNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();
        assertThat(notification.getTitle()).isEqualTo(testTitle);
        assertThat(notification.getAuthor().getId()).isEqualTo(testAdminId);
        assertThat(notification.isMain()).isEqualTo(false);
    }

    @Test
    public void 중요_공지사항_작성() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = true;

        // when
        final var result = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getAllNotification();
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = afterNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();
        assertThat(notification.getTitle()).isEqualTo(testTitle);
        assertThat(notification.getAuthor().getId()).isEqualTo(testAdminId);
        assertThat(notification.isMain()).isEqualTo(true);
    }

    @Test
    public void 공지사항_삭제() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = notificationService.getAllNotification();
        assertThat(notification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notificationBean = notification.getResponse().stream().findFirst().orElse(null);
        assertThat(notificationBean).isNotNull();

        final var notificationId = notificationBean.getId();

        // when
        final var result = notificationService.deleteNotification(notificationId, testAdminId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getAllNotification();
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(afterNotification.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 없는_공지사항_삭제() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var notificationId = 0L;

        // when
        final var result = notificationService.deleteNotification(notificationId, testAdminId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.NOTIFICATION_NOT_EXIST.getMessage());
    }

    @Test
    public void 일반_사용자가_공지사항_삭제() {
        // given
        testAccountManager.process();
        final var testId = TestAccountManager.testMember.getId();
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = notificationService.getAllNotification();
        assertThat(notification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notificationBean = notification.getResponse().stream().findFirst().orElse(null);
        assertThat(notificationBean).isNotNull();

        final var notificationId = notificationBean.getId();

        // when
        final var result = notificationService.deleteNotification(notificationId, testId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION.getMessage());
    }

    @Test
    public void 중요_공지사항_목록_조회() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = true;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        // when
        final var result = notificationService.getAllMainNotification();

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isGreaterThan(0);

        final var notification = result.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();
        assertThat(notification.getTitle()).isEqualTo(testTitle);
        assertThat(notification.getAuthor().getId()).isEqualTo(testAdminId);
        assertThat(notification.isMain()).isEqualTo(true);
    }

    @Test
    public void 없는_중요_공지사항_목록_조회() {
        // given
        // 공지사항을 작성하지 않음
        
        // when
        final var result = notificationService.getAllMainNotification();

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().size()).isEqualTo(0);
    }

    @Test
    public void 공지사항_내용_조회() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getAllNotification();
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = afterNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();

        final var testNotificationId = notification.getId();

        // when
        final var result = notificationService.getNotification(testNotificationId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getTitle()).isEqualTo(testTitle);
        assertThat(result.getResponse().getAuthor().getId()).isEqualTo(testAdminId);
        assertThat(result.getResponse().isMain()).isEqualTo(false);
    }

    @Test
    public void 중요_공지사항_내용_조회() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = true;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getAllNotification();
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = afterNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();

        final var testNotificationId = notification.getId();

        // when
        final var result = notificationService.getNotification(testNotificationId);

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(result.getResponse().getTitle()).isEqualTo(testTitle);
        assertThat(result.getResponse().getAuthor().getId()).isEqualTo(testAdminId);
        assertThat(result.getResponse().isMain()).isEqualTo(true);
    }

    @Test
    public void 공지사항_수정() {
        // given
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var getNotification = notificationService.getAllNotification();
        assertThat(getNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = getNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();

        final var testNotificationId = notification.getId();
        final var updateTestTitle = "수정된 제목";
        final var updateTestContent = "수정된 내용";
        final var updateIsMain = true;

        // when
        final var result = notificationService.updateNotification(
                testNotificationId,
                testAdminId,
                updateTestTitle,
                updateTestContent,
                updateIsMain
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var afterNotification = notificationService.getNotification(testNotificationId);
        assertThat(afterNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());
        assertThat(afterNotification.getResponse().getTitle()).isEqualTo(updateTestTitle);
        assertThat(afterNotification.getResponse().getContent()).isEqualTo(updateTestContent);
        assertThat(afterNotification.getResponse().isMain()).isEqualTo(updateIsMain);
    }

    @Test
    public void 없는_공지사항_수정() {
        // given
        final var testNotificationId = 0L;
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var updateTestTitle = "수정된 제목";
        final var updateTestContent = "수정된 내용";
        final var updateIsMain = true;

        // when
        final var result = notificationService.updateNotification(
                testNotificationId,
                testAdminId,
                updateTestTitle,
                updateTestContent,
                updateIsMain
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.NOTIFICATION_NOT_EXIST.getMessage());
    }

    @Test
    public void 일반_사용자가_공지사항_수정() {
        // given
        testAccountManager.process();
        final var testMemberId = TestAccountManager.testMember.getId();
        final var testAdminId = TestAdminAccountManager.testAdminMember.getId();
        final var testTitle = "제목";
        final var testContent = "내용";
        final var isMain = false;

        final var write = notificationService.writeNotification(testAdminId, testTitle, testContent, isMain);
        assertThat(write.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var getNotification = notificationService.getAllNotification();
        assertThat(getNotification.getResult()).isEqualTo(ErrorMessage.SUCCESS.getMessage());

        final var notification = getNotification.getResponse().stream().findFirst().orElse(null);
        assertThat(notification).isNotNull();

        final var testNotificationId = notification.getId();
        final var updateTestTitle = "수정된 제목";
        final var updateTestContent = "수정된 내용";
        final var updateIsMain = true;

        // when
        final var result = notificationService.updateNotification(
                testNotificationId,
                testMemberId,
                updateTestTitle,
                updateTestContent,
                updateIsMain
        );

        // then
        assertThat(result.getResult()).isEqualTo(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION.getMessage());
    }
}
