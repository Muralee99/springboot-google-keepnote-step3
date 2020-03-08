package com.stackroute.keepnote.dao;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
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
public class UserDaoImpl implements UserDAO {

	/*
     * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	/*
	 * Create a new user
	 */

    public boolean registerUser(User user) {

        boolean isUserCreated = true;
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
        } catch (Exception exception) {
            isUserCreated = false;
        }
        return isUserCreated;
    }

	/*
	 * Update an existing user
	 */

    public boolean updateUser(User user) {

        boolean isUserUpdated = true;
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
        } catch (Exception exception) {
            isUserUpdated = false;
        }
        return isUserUpdated;

    }

    /*
     * Retrieve details of a specific user
     */
    public User getUserById(String UserId) {


        String hql = "from User where user_id= ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, UserId);

        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) query.list();

        if (listUser != null && !listUser.isEmpty()) {
            return listUser.get(0);
        }

        return null;
    }

	/*
	 * validate an user
	 */

    public boolean validateUser(String userId, String password) throws UserNotFoundException {
        String hql = "from User where user_id= ? and user_password =?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, userId).setParameter(1, password);

        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) query.list();

        if (listUser != null && !listUser.isEmpty()) {
            return true;
        } else {
            throw new UserNotFoundException("");
        }


    }

    /*
     * Remove an existing user
     */
    public boolean deleteUser(String userId) {
        boolean isUserDeleted = true;

        User getUser = getUserById(userId);

        if (getUser == null) {
            return false;
        } else {

            User user = new User();
            user.setUserId(userId);
            try {
                sessionFactory.getCurrentSession().delete(user);
            } catch (Exception exception) {
                isUserDeleted = false;
            }
        }
        return isUserDeleted;

    }

}
