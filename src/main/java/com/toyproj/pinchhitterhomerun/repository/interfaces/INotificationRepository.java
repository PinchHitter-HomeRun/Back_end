package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Notification;

import java.time.LocalDateTime;
import java.util.Collection;

public interface INotificationRepository {
    boolean save(Notification notification);
    Collection<Notification> findAllNotification();
    Collection<Notification> findMainNotification();
    Notification findNotificationById(Long id);
}
