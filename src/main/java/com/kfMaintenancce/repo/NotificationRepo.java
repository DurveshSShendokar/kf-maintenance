package com.kfMaintenancce.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.Notification;



public interface NotificationRepo extends JpaRepository<Notification, Integer>,NotificationRepoCustom{
	@Query("from Notification n where n.viewed=0")
	List<Notification> getAllNotificationUnView();

    @Query("from Notification n where n.viewed=0")
    List<Notification> getAllNotificationView();

	@Query("from Notification n where n.machine.machine_id=?1 and n.viewed=0")
	List<Notification> getAllNotificationByMachine(int machine_id);
	@Query("select count(*) from Notification n where n.notificationFor IN ('OWNER', 'ADMIN') and n.notificationDept = ?1 and n.viewed = 0")
	int getNotificationCountByDept(String deptName);

	@Query("select count(*) from Notification n where n.notificationFor='User' and n.notificationDept='IT' and n.notificationForSpecId=?1 and n.viewed=0")
	List<Notification> getUserNotificationById(int userId);
	
	@Query("from Notification n where n.notificationFor IN ('OWNER', 'ADMIN','Engineer') and n.notificationDept = ?1 and n.viewed = 0 order by n.raisedTime desc")
	List<Notification> getNotificationListByDept(String deptName);


	 @Query("SELECT CASE WHEN COUNT(n) > 0 THEN TRUE ELSE FALSE END " +
	           "FROM Notification n " +
	           "WHERE n.notificationForSpecId = :complaintId " +
	           "AND n.notificationFor = :notificationFor")
	    boolean existsByComplaintAndType(@Param("complaintId") int complaintId,
	                                     @Param("notificationFor") String notificationFor);
	

	@Query("FROM Notification n WHERE n.notificationForSpecId = ?1 AND n.viewed = 0 ORDER BY n.raisedTime DESC")
	List<Notification> getNotificationsForUser(int userId);

	
	@Query("FROM Notification n WHERE n.notificationFor = :notificationFor")
	List<Notification> getNotificationListByFor(@Param("notificationFor") String notificationFor);

	
	  @Modifying
	    @Transactional
	    @Query("UPDATE Notification n SET n.viewed = 1, n.viewedTime = CURRENT_TIMESTAMP WHERE n.id = :id")
	    void markAsViewed(@Param("id") int id);




    @Query("FROM Notification n WHERE n.notificationDept = :deptName")
    List<Notification> getAllByDept(@Param("deptName") String deptName);

    @Query("FROM Notification n WHERE n.viewed = 0 AND n.notificationDept = :deptName")
    List<Notification> getAllUnViewedByDept(@Param("deptName") String deptName);

    @Query("FROM Notification n WHERE n.viewed = 1 AND n.notificationDept = :deptName")
    List<Notification> getAllViewedByDept(@Param("deptName") String deptName);


}
