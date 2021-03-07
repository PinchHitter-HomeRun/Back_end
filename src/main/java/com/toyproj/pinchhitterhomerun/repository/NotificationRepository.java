package com.toyproj.pinchhitterhomerun.repository;

import com.toyproj.pinchhitterhomerun.entity.Notification;
import com.toyproj.pinchhitterhomerun.repository.interfaces.INotificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
    public Collection<Notification> findAllNotification() {
        return em.createQuery("select n from Notification  n where n.deletedDate is null", Notification.class)
                .getResultList();
    }

    @Override
    public Collection<Notification> findMainNotification() {
        return em.createQuery("select n from Notification n where n.isMain = true and n.deletedDate is null", Notification.class)
                .getResultList();
    }

    @Override
    public Notification findNotificationById(Long id) {
        try {
            return em.createQuery("select n from Notification n where n.id = :id and n.deletedDate is null", Notification.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
