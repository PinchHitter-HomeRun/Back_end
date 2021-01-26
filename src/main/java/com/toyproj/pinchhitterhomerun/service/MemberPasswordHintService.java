package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.PasswordHint;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.repository.MemberPasswordHintRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MemberPasswordHintService {

    private final MemberPasswordHintRepository memberPasswordHintRepository;

    // 힌트 텍스트
    public ServiceResult<PasswordHint> getPasswordHint(String loginId, String birthDay) {
        final var memberHint = memberPasswordHintRepository.findByMemberId(loginId, birthDay);

        if (memberHint == null) {
            return new ServiceResult<>(ErrorMessage.PASSWORD_HINT_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, memberHint.getHintId());
    }

    // 힌트 답변 매핑
    public ServiceResult<Boolean> matchHintAnswer(String loginId, String birthDay, String answer) {
        final var memberHint = memberPasswordHintRepository.findByMemberId(loginId, birthDay);

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
