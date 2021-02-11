package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.MemberPasswordHint;
import com.toyproj.pinchhitterhomerun.entity.PasswordHint;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.responsebean.MemberHintAns;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberPasswordHintService {

    @Autowired
    MemberPasswordHintRepository memberPasswordHintRepository;

    @Autowired
    MemberRepository memberRepository;

    // 힌트 가져오기
    public ServiceResult<MemberHintAns> getPasswordHint(String loginId, String birthDay) {
        final var result = new MemberHintAns();
        final var findMember = memberRepository.findByLoginIdAndBirthDay(loginId, birthDay);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var memberHint = memberPasswordHintRepository.findByMember(findMember);

        if (memberHint == null) {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_FOUND);
        }

        result.setId(memberHint.getId());
        result.setHintText(memberHint.getHintId().getText());

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    // 힌트 답변 매핑
    public ServiceResult<Boolean> matchHintAnswer(Long memberHintId, String answer) {
        System.out.println(memberHintId);
        final var memberHint = memberPasswordHintRepository.findById(memberHintId);

        if (memberHint == null) {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_FOUND);
        }

        if (memberHint.getAnswer().equals(answer)) {
            return new ServiceResult<>(ErrorMessage.SUCCESS, true);
        } else {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_MATCHED);
        }
    }
}
