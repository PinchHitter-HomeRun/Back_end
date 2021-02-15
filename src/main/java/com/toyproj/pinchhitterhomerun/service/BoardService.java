package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.BoardException;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.exception.MemberException;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.requestbean.BoardReq;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.MatchType;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    /**
     * 모든 게시글 조회
     */
    public ServiceResult<Collection<Board>> getAllBoards() {
        final var result = boardRepository.findAll();

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 해당 id의 게시글 조회
     */
    public ServiceResult<Board> getBoard(Long id) {
        final var result = boardRepository.findById(id);

        if (result == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_EXIST);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 게시글 작성
     */
    public ServiceResult<Void> writeBoard(Long MemberId, String title, String content, PayType payType,
                                          int pay, LocalDateTime startDate, LocalDateTime endDate) {
        final Board board = new Board();
        final var maxBoards = 3;

        final var findMember = memberRepository.findById(MemberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        if (findMember.getBranch() == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_MEMBER_HAVE_NOT_BRANCH);
        }

        final var findBoards = boardRepository.findByMember(findMember);

        if (findBoards.size() >= maxBoards) {
            return new ServiceResult<>(ErrorMessage.BOARD_CANNOT_WRITE_NO_MORE);
        }

        if (startDate.isBefore(TimeManager.now().plusDays(1))) {
            return new ServiceResult<>(ErrorMessage.BOARD_HAVE_TO_24_HOURS_BEFORE);
        }

        if (startDate.isAfter(endDate)) {
            return new ServiceResult<>(ErrorMessage.BOARD_INVALID_DATE_TIME);
        }

        board.writeBoard(findMember, title, content, payType, pay, startDate, endDate);

        if (!boardRepository.save(board)) {
            throw new BoardException(ErrorMessage.BOARD_DB_ERROR);
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 사용자 id로 게시글 조회
     */
    public ServiceResult<Collection<Board>> getBoardsByMemberId(Long memberId) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var result = boardRepository.findByMember(findMember);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 특정 브랜드의 모든 게시글 조회
     */
    public ServiceResult<Collection<Board>> getBoardsByBrandId(Long brandId) {
        final var findBrand = brandRepository.findById(brandId);

        if (findBrand == null) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_EXIST);
        }

        final var result = boardRepository.findByBrandId(brandId);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 특정 지점의 모든 게시글 조회
     */
    public ServiceResult<Collection<Board>> getBoardsByBranchId(Long branchId) {
        final var findBranch = branchRepository.findById(branchId);

        if (findBranch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        final var result = boardRepository.findByBranchId(branchId);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 게시글 수정
     */
    public ServiceResult<Void> updateBoard(Long memberId, Long id, String title, String content, PayType payType,
                                            int pay, LocalDateTime startDate, LocalDateTime endDate) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var findBoard = boardRepository.findById(id);

        if (findBoard == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_EXIST);
        }

        if (!findBoard.getMember().getId().equals(memberId)) {
            return new ServiceResult<>(ErrorMessage.BOARD_CAN_NOT_UPDATE_NOT_MINE);
        }

        if (startDate.isBefore(TimeManager.now().plusDays(1))) {
            return new ServiceResult<>(ErrorMessage.BOARD_HAVE_TO_24_HOURS_BEFORE);
        }

        if (startDate.isAfter(endDate)) {
            return new ServiceResult<>(ErrorMessage.BOARD_INVALID_DATE_TIME);
        }

        findBoard.updateBoard(title, content, payType, pay, startDate, endDate);

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 사용자 이름으로 게시글 조회 (이름이 표함된 모든 게시글 조회) (2글자 이상)
     */
    public ServiceResult<Collection<Board>> getBoardByContainsName(String name) {
        if (name.length() < 2) {
            return new ServiceResult<>(ErrorMessage.BOARD_SEARCH_KEYWORD_HAVE_TO_OVER_TWO_LETTERS);
        }

        final var findMember = memberRepository.findByContainsName("%" + name +"%");

        if (findMember.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.SUCCESS, new ArrayList<>());
        }

        final var findBoards = boardRepository.findByMembers(findMember);

        return new ServiceResult<>(ErrorMessage.SUCCESS, findBoards);
    }

    /**
     * 게시글 삭제
     */
    public ServiceResult<Void> deleteBoard(Long memberId, Long id) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var findBoard = boardRepository.findById(id);

        if (findBoard == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_EXIST);
        }

        if (!findBoard.getMember().getId().equals(memberId)) {
            return new ServiceResult<>(ErrorMessage.BOARD_CAN_NOT_UPDATE_NOT_MINE);
        }

        findBoard.updateDeleteTime();

        return new ServiceResult<>(ErrorMessage.SUCCESS);
    }

    /**
     * 브랜드와 지역(시, 구, 동)으로 게시글 조회
     */
    public ServiceResult<Collection<Board>> getBoardsByAddress(Long brandId, String city, String gu, String street) {
        final var findBrand = brandRepository.findById(brandId);

        if (findBrand == null) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_EXIST);
        }

        final var branches = branchRepository.findByBrandId(brandId);

        final var filteredBranches = branches
                .stream()
                .filter(x -> x.getAddress().getCity().equals(city) &&
                        x.getAddress().getGu().equals(gu) &&
                        x.getAddress().getSub().startsWith(street))
                .collect(Collectors.toList());

        final List<Board> result = new ArrayList<>();

        for (var branch : filteredBranches) {
            final var boards = boardRepository.findByBranchId(branch.getId());

            if (boards != null) {
                result.addAll(boards);
            }
        }

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }
}
