package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.response.MemberHintRes;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberPasswordHintService {

    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;

    @Autowired
    MemberRepository memberRepository;

    // 힌트 가져오기
    public ServiceResult<MemberPasswordHint> getPasswordHint(String loginId, String birthDay) {
        final var findMember = memberRepository.findByLoginIdAndBirthDay(loginId, birthDay);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var memberHint = memberPasswordHintRepository.findByMember(findMember);

        if (memberHint == null) {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, memberHint);
    }

    // 힌트 답변 매핑
    public ServiceResult<Boolean> matchHintAnswer(Long memberHintId, String answer) {
        final var memberHint = memberPasswordHintRepository.findById(memberHintId);

        if (memberHint == null) {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_FOUND);
        }

        if (memberHint.getAnswer().equals(answer)) {
            return new ServiceResult<>(ErrorMessage.SUCCESS, true);
        }

        return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_MATCHED);
    }
}
