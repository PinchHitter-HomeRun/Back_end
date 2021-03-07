package com.toyproj.pinchhitterhomerun.controller;

import com.toyproj.pinchhitterhomerun.bean.NotificationTitleResultBean;
import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.entity.ResponseResult;
import com.toyproj.pinchhitterhomerun.request.NotificationWriteReq;
import com.toyproj.pinchhitterhomerun.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/board/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @ApiOperation("모든 공지사항 리스트 조회")
    @GetMapping
    public ResponseResult<Collection<NotificationTitleResultBean>> getAllNotification() {
        final var result = notificationService.getAllNotification();

        return new ResponseResult<>(result.getResponse());
    }

    @ApiOperation("모든 중요 공지사항 리스트 조회")
    @GetMapping("/main")
    public ResponseResult<Collection<NotificationTitleResultBean>> getAllMainNotification() {
        final var result = notificationService.getAllMainNotification();

        return new ResponseResult<>(result.getResponse());
    }

    @ApiOperation("공지사항 작성")
    @PutMapping
    public ResponseResult<Void> writeNotification(@RequestBody NotificationWriteReq request) {
        final var result = notificationService.writeNotification(
                request.getAdminId(),
                request.getTitle(),
                request.getContent(),
                request.isMain()
        );

        return new ResponseResult<>(result.getResponse());
    }
    
    @ApiOperation("공지사항 수정")
    @PutMapping("/{notificationId}")
    public ResponseResult<Void> updateNotification(@PathVariable("notificationId") Long notificationId,
                                                   @RequestBody NotificationWriteReq request) {
        final var result = notificationService.updateNotification(
                notificationId,
                request.getAdminId(),
                request.getTitle(),
                request.getContent(),
                request.isMain()
        );

        return new ResponseResult<>(result);
    }
    
    // 삭제
    @ApiOperation("공지사항 삭제")
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
