package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.repository.interfaces.INotificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class NotificationRepository implements INotificationRepository {

    final EntityManager em;

    public NotificationRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean save(Notification notification) {
        try {
            em.persist(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Collection<Notification> getAllNotification() {
        return em.createQuery("select n from Notification  n", Notification.class)
                .getResultList();
    }

    @Override
    public Collection<Notification> getMainNotification() {
        return em.createQuery("select n from Notification n where n.isMain = true", Notification.class)
                .getResultList();
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        try {
            return em.find(Notification.class, notificationId);
        } catch (Exception e) {
            return null;
        }
    }
}
