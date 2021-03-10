package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.bean.NotificationTitleResultBean;
import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.NotificationWriteReq;
import com.toyproj.pinchhitterhomerun.service.NotificationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @ApiOperation("모든 공지사항 리스트 조회")
    @ResponseBody
    @GetMapping
    public ResponseResult<Collection<NotificationTitleResultBean>> getAllNotification() {
        final var result = notificationService.getAllNotification();

        return new ResponseResult<>(result.getResponse());
    }

    @ApiOperation("모든 중요 공지사항 리스트 조회")
    @ResponseBody
    @GetMapping("/main")
    public ResponseResult<Collection<NotificationTitleResultBean>> getAllMainNotification() {
        final var result = notificationService.getAllMainNotification();

        return new ResponseResult<>(result.getResponse());
    }

    @ApiOperation("공지사항 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "공지사항 작성 request\n" +
                    "adminId : 관리자 ID\n" +
                    "title : 제목\n" +
                    "content : 내용\n" +
                    "isMain : 중요 공지인지 여부(true or false)",
                    required = true,
                    dataType = "NotificationWriteReq")
    })
    @ResponseBody
    @PutMapping
    public ResponseResult<Void> writeNotification(@RequestBody NotificationWriteReq request) {
        final var result = notificationService.writeNotification(
                request.getAdminId(),
                request.getTitle(),
                request.getContent(),
                request.getIsMain()
        );

        return new ResponseResult<>(result.getResponse());
    }
    
    @ApiOperation("공지사항 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notificationId", value = "공지사항 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "request", value = "공지사항 작성 request\n" +
                    "adminId : 관리자 ID\n" +
                    "title : 제목\n" +
                    "content : 내용\n" +
                    "isMain : 중요 공지인지 여부(true or false)",
                    dataType = "NotificationWriteReq")
    })
    @ResponseBody
    @PutMapping("/{notificationId}")
    public ResponseResult<Void> updateNotification(@PathVariable("notificationId") Long notificationId,
                                                   @RequestBody NotificationWriteReq request) {
        final var result = notificationService.updateNotification(
                notificationId,
                request.getAdminId(),
                request.getTitle(),
                request.getContent(),
                request.getIsMain()
        );

        return new ResponseResult<>(result);
    }
    
    // 삭제
    @ApiOperation("공지사항 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notificationId", value = "공지사항 ID", required = true, dataType = "long", example = "0"),
            @ApiImplicitParam(name = "adminId", value = "관리자 ID", required = true, dataType = "long", example = "0")
    })
    @ResponseBody
    @DeleteMapping("/{notificationId}")
    public ResponseResult<Void> deleteNotification(@PathVariable("notificationId") Long notificationId,
                                                   @RequestParam Long adminId) {
        final var result = notificationService.deleteNotification(
                notificationId,
                adminId
        );

        return new ResponseResult<>(result);
    }
}
