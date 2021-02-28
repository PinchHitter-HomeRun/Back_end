package com.toyproj.pinchhitterhomerun.repository.interfaces;

import com.toyproj.pinchhitterhomerun.entity.Notification;

import java.util.Collection;

public interface INotificationRepository {
    boolean save(Notification notification);
    Collection<Notification> getAllNotification();
    Collection<Notification> getMainNotification();
    Notification getNotificationById(Long notificationId);
}
