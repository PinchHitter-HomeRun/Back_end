package com.toyproj.pinchhitterhomerun.helper;

import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.SexType;
import com.toyproj.pinchhitterhomerun.type.SnsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestAdminAccountManager {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordHintRepository passwordHintRepository;

    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BranchRequestRepository branchRequestRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public static Member testAdminMember;

    public void process() {
        testAdminMember = memberRepository.findByLoginId("testAdminAccount@daeta.com");

        if (testAdminMember == null) {
            final var createMember = Member.builder()
                    .birthDay("930903")
                    .branch(null)
                    .loginId("testAdminAccount@daeta.com")
                    .name("테스트관리자계정")
                    .passWord("30b4559e8f0435b14b9eca6b98d33f28d9fdd42ecd1ef1f5094470b592752d2a")
                    .phone("01012345678")
                    .role(roleRepository.findById(1L))
                    .sns(SnsType.None)
                    .sex(SexType.Male)
                    .build();

            final var hint = passwordHintRepository.findById(1L);

            if (memberRepository.save(createMember)) {
                testAdminMember = memberRepository.findByLoginId("testAdminAccount@daeta.com");

                final var memberPasswordHint = new MemberPasswordHint(testAdminMember, hint, "답변");
                if (!memberPasswordHintRepository.save(memberPasswordHint)) {
                    throw new TestAccountManagerException("TestAdminAccount SetHint Failed");
                }
            } else {
                throw new TestAccountManagerException("TestAdminAccount Create Failed");
            }

            testAdminMember.setAdminPermission(true);
        }
    }

    public void deleteAllNotification() {
        final var notifications = notificationRepository.findAllNotification();
        notifications.forEach(Notification::deleteNotification);
    }
}
