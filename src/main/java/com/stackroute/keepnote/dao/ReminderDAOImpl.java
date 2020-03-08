package com.stackroute.keepnote.dao;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class ReminderDAOImpl implements ReminderDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    public ReminderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	/*
	 * Create a new reminder
	 */

    public boolean createReminder(Reminder reminder) {
        boolean isReminderCreated = true;

		/*Reminder reminder1 =  null;

		try {
			reminder1 = getReminderById(reminder.getReminderId());
		} catch (ReminderNotFoundException e) {
			e.printStackTrace();
		}*/

        try {
            //if(reminder1 != null) {
            sessionFactory.getCurrentSession().saveOrUpdate(reminder);
            //	}
        } catch (Exception exception) {
            isReminderCreated = false;
        }
        return isReminderCreated;

    }
	
	/*
	 * Update an existing reminder
	 */

    public boolean updateReminder(Reminder reminder) {
        boolean isReminderUpdated = true;

        Reminder reminder1 = null;

        try {
            reminder1 = getReminderById(reminder.getReminderId());
        } catch (ReminderNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (reminder1 != null) {
                sessionFactory.getCurrentSession().saveOrUpdate(reminder1);
            }
        } catch (Exception exception) {
            isReminderUpdated = false;
        }
        return isReminderUpdated;

    }

	/*
	 * Remove an existing reminder
	 */

    public boolean deleteReminder(int reminderId) {
        boolean isReminderDeleted = true;
        Reminder reminder = new Reminder();
        reminder.setReminderId(reminderId);
        try {
            sessionFactory.getCurrentSession().delete(reminder);
        } catch (Exception exception) {
            isReminderDeleted = false;
        }
        return isReminderDeleted;

    }

	/*
	 * Retrieve details of a specific reminder
	 */

    public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
        String hql = "from Reminder where reminder_id= ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, reminderId);

        @SuppressWarnings("unchecked")
        List<Reminder> listReminder = (List<Reminder>) query.list();

        if (listReminder != null && !listReminder.isEmpty()) {
            return listReminder.get(0);
        } else {
            throw new ReminderNotFoundException("");
        }


    }

	/*
	 * Retrieve details of all reminders by userId
	 */

    public List<Reminder> getAllReminderByUserId(String userId) {
        String hql = "from Reminder where reminder_created_by= ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, userId);

        @SuppressWarnings("unchecked")
        List<Reminder> listReminders = (List<Reminder>) query.list();

        if (listReminders != null && !listReminders.isEmpty()) {
            return listReminders;
        }

        return null;

    }

}
