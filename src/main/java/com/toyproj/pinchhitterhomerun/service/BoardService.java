package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.BoardException;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.BoardRepository;
import com.toyproj.pinchhitterhomerun.repository.BranchRepository;
import com.toyproj.pinchhitterhomerun.repository.BrandRepository;
import com.toyproj.pinchhitterhomerun.repository.MemberRepository;
import com.toyproj.pinchhitterhomerun.requestbean.BoardReq;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BranchRepository branchRepository;

    public ServiceResult<Collection<Board>> getAllBoard() {
        final var result = boardRepository.findAll();

        if (result.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    public ServiceResult<Void> writeBoard(Long MemberId, BoardReq request) {
        final Board board = new Board();

        final var findMember = memberRepository.findById(MemberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (findMember.getBranch() == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_MEMBER_HAVE_NOT_BRANCH);
        }

        if (request.getStartDate().minusDays(1).isAfter(TimeManager.now())) {
            return new ServiceResult<>(ErrorMessage.BOARD_HAVE_TO_24_HOURS_BEFORE);
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            return new ServiceResult<>(ErrorMessage.BOARD_INVALID_DATE_TIME);
        }

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setMember(findMember);
        board.setBrandId(findMember.getBranch().getBrand().getId());
        board.setBranchId(findMember.getBranch().getId());
        board.setStartDate(request.getStartDate());
        board.setEndDate(request.getEndDate());
        board.setPayType(request.getPayType());
        board.setPay(request.getPay());

        final var result = boardRepository.save(board);

        if (!result) {
            throw new BoardException(ErrorMessage.BOARD_DB_ERROR);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    public ServiceResult<Collection<Board>> getBoardByMemberId(Long memberId) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var result = boardRepository.findByMember(findMember);

        if (result.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    public ServiceResult<Collection<Board>> getBoardByBrandId(Long brandId) {
        final var result = boardRepository.findByBrandId(brandId);

        if (result.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    public ServiceResult<Collection<Board>> getMBoardByBranchId(Long branchId) {
        final var result = boardRepository.findByBranchId(branchId);

        if (result.size() == 0) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_FOUND);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }
}
