package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.bean.BoardTitleResultBean;
import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.bean.NotificationContentResultBean;
import com.toyproj.pinchhitterhomerun.bean.NotificationTitleResultBean;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.repository.NotificationRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    MemberRepository memberRepository;

    private MemberBoardBean convertToMemberBean(Member member) {
        final var memberBoardBean = new MemberBoardBean();

        memberBoardBean.setMemberProperty(member);

        return memberBoardBean;
    }

    private Collection<NotificationTitleResultBean> getNotificationBean(Collection<Notification> notifications) {
        final var result = new ArrayList<NotificationTitleResultBean>();

        notifications.forEach(x -> {
                    final var notificationTitle = new NotificationTitleResultBean();
                    notificationTitle.setId(x.getId());
                    notificationTitle.setAuthor(convertToMemberBean(x.getMember()));
                    notificationTitle.setTitle(x.getTitle());
                    notificationTitle.setMain(x.isMain());
                    notificationTitle.setWriteDate(x.getCreatedDate());

                    result.add(notificationTitle);
                }
        );

        return result;
    }

    /**
     * 모든 공지사항 조회
     */
    public ServiceResult<Collection<NotificationTitleResultBean>> getAllNotification() {
        final var notifications = notificationRepository.findAllNotification();

        final var result = getNotificationBean(notifications);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 중요한 공지사항만 조회
     */
    public ServiceResult<Collection<NotificationTitleResultBean>> getAllMainNotification() {
        final var notifications = notificationRepository.findMainNotification();

        final var result = getNotificationBean(notifications);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 공지사항 내용 조회
     */
    public ServiceResult<NotificationContentResultBean> getNotification(Long notificationId) {
        final var notificationInfo = notificationRepository.findNotificationById(notificationId);

        if (notificationInfo == null) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_NOT_EXIST);
        }

        final var result = new NotificationContentResultBean();
        final var memberBean = new MemberBoardBean();

        memberBean.setMemberProperty(notificationInfo.getMember());

        result.setId(notificationInfo.getId());
        result.setAuthor(memberBean);
        result.setTitle(notificationInfo.getTitle());
        result.setContent(notificationInfo.getContent());
        result.setMain(notificationInfo.isMain());
        result.setWriteDate(notificationInfo.getCreatedDate());

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 공지사항 작성
     */
    public ServiceResult<Void> writeNotification(Long adminMemberId, String title, String content, boolean isMain) {
        final var memberInfo = memberRepository.findById(adminMemberId);

        if (memberInfo == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (!memberInfo.isAdmin()) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION);
        }

        final var notification = new Notification();
        notification.writeNotification(memberInfo, title, content, isMain);

        if (!notificationRepository.save(notification)) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_DB_ERROR);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 공지사항 수정
     */
    public ServiceResult<Void> updateNotification(Long notificationId, Long adminMemberId, String title,
                                                  String content, boolean isMain) {
        final var memberInfo = memberRepository.findById(adminMemberId);

        if (memberInfo == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (!memberInfo.isAdmin()) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION);
        }

        final var notificationInfo = notificationRepository.findNotificationById(notificationId);

        if (notificationInfo == null) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_NOT_EXIST);
        }

        if (!notificationInfo.getMember().getId().equals(adminMemberId)) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION);
        }

        notificationInfo.updateNotification(title, content, isMain);

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 공지사항 삭제
     */
    public ServiceResult<Void> deleteNotification(Long notificationId, Long adminMemberId) {
        final var memberInfo = memberRepository.findById(adminMemberId);

        if (memberInfo == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (!memberInfo.isAdmin()) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_HAVE_NOT_PERMISSION);
        }

        final var notificationInfo = notificationRepository.findNotificationById(notificationId);

        if (notificationInfo == null) {
            return new ServiceResult<>(ErrorMessage.NOTIFICATION_NOT_EXIST);
        }

        notificationInfo.deleteNotification();

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }
}
