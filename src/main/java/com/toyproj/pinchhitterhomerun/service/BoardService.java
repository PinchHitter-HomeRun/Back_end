package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.exception.BoardException;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.repository.BoardRepository;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getMembersBoard(Long memberId) {
        List<Board> result = boardRepository.findByMemberId(memberId);

        if (result.size() == 0) {
            throw new BoardException(ErrorMessage.BOARD_NOT_FOUND);
        }

        return result;
    }

    public List<Board> getMembersBoardByLoginId(String loginId) {
        List<Board> result = boardRepository.findByMemberLoginId(loginId);

        if (result.size() == 0) {
            throw new BoardException(ErrorMessage.BOARD_NOT_FOUND);
        }

        return result;
    }
}
