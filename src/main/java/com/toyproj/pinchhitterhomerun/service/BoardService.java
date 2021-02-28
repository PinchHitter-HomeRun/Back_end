package com.toyproj.pinchhitterhomerun.service;

import com.toyproj.pinchhitterhomerun.bean.BoardContentResultBean;
import com.toyproj.pinchhitterhomerun.bean.BoardTitleResultBean;
import com.toyproj.pinchhitterhomerun.bean.MemberBoardBean;
import com.toyproj.pinchhitterhomerun.entity.Member;
import com.toyproj.pinchhitterhomerun.entity.ServiceResult;
import com.toyproj.pinchhitterhomerun.exception.BoardException;
import com.toyproj.pinchhitterhomerun.entity.Board;
import com.toyproj.pinchhitterhomerun.repository.*;
import com.toyproj.pinchhitterhomerun.type.ErrorMessage;
import com.toyproj.pinchhitterhomerun.type.PayType;
import com.toyproj.pinchhitterhomerun.util.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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

    private MemberBoardBean convertToMemberBean(Member member) {
        final var memberBoardBean = new MemberBoardBean();

        memberBoardBean.setMemberProperty(member);

        return memberBoardBean;
    }

    private Collection<BoardTitleResultBean> getBoardBean(Collection<Board> boards) {
        final var result = new ArrayList<BoardTitleResultBean>();

        boards.forEach(x -> {
                    final var bean = new BoardTitleResultBean();

                    bean.setId(x.getId());
                    bean.setAuthor(convertToMemberBean(x.getMember()));
                    bean.setTitle(x.getTitle());
                    bean.setWriteDate(x.getCreatedDate());
                    bean.setStarDate(x.getStartDate());
                    bean.setEndDate(x.getEndDate());
                    bean.setPayType(x.getPayType());
                    bean.setPay(x.getPay());
                    bean.setMatchType(x.getMatchType());

                    result.add(bean);
                }
        );

        return result;
    }

    /**
     * 모든 게시글 조회
     */
    public ServiceResult<Collection<BoardTitleResultBean>> getAllBoards() {
        final var boards = boardRepository.findAll();

        final var result = getBoardBean(boards);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 해당 id의 게시글 조회
     */
    public ServiceResult<BoardContentResultBean> getBoard(Long id) {
        final var board = boardRepository.findById(id);

        if (board == null) {
            return new ServiceResult<>(ErrorMessage.BOARD_NOT_EXIST);
        }

        final var result = new BoardContentResultBean();
        result.setId(board.getId());
        result.setAuthor(convertToMemberBean(board.getMember()));
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setWriteDate(board.getCreatedDate());
        result.setStarDate(board.getStartDate());
        result.setEndDate(board.getEndDate());
        result.setPayType(board.getPayType());
        result.setPay(board.getPay());
        result.setMatchType(board.getMatchType());

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
    public ServiceResult<Collection<BoardTitleResultBean>> getBoardsByMemberId(Long memberId) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var boards = boardRepository.findByMember(findMember);
        final var result = getBoardBean(boards);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 특정 브랜드의 모든 게시글 조회
     */
    public ServiceResult<Collection<BoardTitleResultBean>> getBoardsByBrandId(Long brandId) {
        final var findBrand = brandRepository.findById(brandId);

        if (findBrand == null) {
            return new ServiceResult<>(ErrorMessage.BRAND_NOT_EXIST);
        }

        final var boards = boardRepository.findByBrandId(brandId);
        final var result = getBoardBean(boards);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 특정 지점의 모든 게시글 조회
     */
    public ServiceResult<Collection<BoardTitleResultBean>> getBoardsByBranchId(Long branchId) {
        final var findBranch = branchRepository.findById(branchId);

        if (findBranch == null) {
            return new ServiceResult<>(ErrorMessage.BRANCH_NOT_EXIST);
        }

        final var boards = boardRepository.findByBranchId(branchId);
        final var result = getBoardBean(boards);

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
    public ServiceResult<Collection<BoardTitleResultBean>> getBoardByContainsName(String name) {
        if (name.length() < 2) {
            return new ServiceResult<>(ErrorMessage.BOARD_SEARCH_KEYWORD_HAVE_TO_OVER_TWO_LETTERS);
        }

        final var findMember = memberRepository.findByContainsName("%" + name +"%");

        if (findMember.isEmpty()) {
            return new ServiceResult<>(ErrorMessage.SUCCESS, new ArrayList<>());
        }

        final var boards = boardRepository.findByMembers(findMember);
        final var result = getBoardBean(boards);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }

    /**
     * 게시글 삭제
     */
    public ServiceResult<Void> deleteBoard(Long memberId, Long boardId) {
        final var findMember = memberRepository.findById(memberId);

        if (findMember == null) {
            return new ServiceResult<>(ErrorMessage.MEMBER_NOT_EXIST);
        }

        final var findBoard = boardRepository.findById(boardId);

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
    public ServiceResult<Collection<BoardTitleResultBean>> getBoardsByAddress(Long brandId, String city, String gu, String street) {
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

        final var boards = new ArrayList<Board>();

        for (var branch : filteredBranches) {
            final var findBoards = boardRepository.findByBranchId(branch.getId());

            if (findBoards != null) {
                boards.addAll(findBoards);
            }
        }

        final var result = getBoardBean(boards);

        return new ServiceResult<>(ErrorMessage.SUCCESS, result);
    }
}
