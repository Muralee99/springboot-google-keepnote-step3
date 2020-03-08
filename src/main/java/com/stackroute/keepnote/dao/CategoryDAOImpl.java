package com.stackroute.keepnote.dao;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
//@Transactional
public class CategoryDAOImpl implements CategoryDAO {

    /*
     * Autowiring should be implemented for the SessionFactory.(Use
     * constructor-based autowiring.
     */
    @Autowired
    private SessionFactory sessionFactory;

    //private Session session;

    public CategoryDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
     * Create a new category
     */
    public boolean createCategory(Category category) {

        boolean isSuccess = true;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean isCategoryCreated = true;
        /*Category category1 = null;

		try {
			category1 = getCategoryById(category.getCategoryId());
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}*/

        try {
            transaction = session.beginTransaction();
            //if(category1 == null)
            sessionFactory.getCurrentSession().persist(category);
            transaction.commit();
        } catch (Exception exception) {
            isCategoryCreated = false;
        } finally {
            session.close();
        }
        return isCategoryCreated;
    }

    /*
     * Remove an existing category
     */
    public boolean deleteCategory(int categoryId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        boolean isCategoryDeleted = true;
        Category category = new Category();
        category.setCategoryId(categoryId);
        try {
            transaction = session.beginTransaction();
            sessionFactory.getCurrentSession().delete(category);
            transaction.commit();
        } catch (Exception exception) {
            isCategoryDeleted = false;
        } finally {
            session.close();
        }
        return isCategoryDeleted;
    }
	/*
	 * Update an existing category
	 */

    public boolean updateCategory(Category category) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        boolean isCategoryUpdated = true;

        Category category1 = null;

		/*try {
			transaction = session.beginTransaction();
			category1 = getCategoryById(category.getCategoryId());
			transaction.commit();
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}*/

        try {
            transaction = session.beginTransaction();
            //if(category1 == null)
            sessionFactory.getCurrentSession().saveOrUpdate(category);
            transaction.commit();
        } catch (Exception exception) {
            isCategoryUpdated = false;
        } finally {
            session.close();
        }

        return isCategoryUpdated;

    }
	/*
	 * Retrieve details of a specific category
	 */

    public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Category where category_id= ?";
        Query query = session.createQuery(hql).setParameter(0, categoryId);

        List<Category> listUser = null;
        try {
            listUser = (List<Category>) query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (listUser != null && !listUser.isEmpty()) {
            return listUser.get(0);
        } else {
            throw new CategoryNotFoundException("");
        }

    }

    /*
     * Retrieve details of all categories by userId
     */
    public List<Category> getAllCategoryByUserId(String userId) {
        String hql = "from Category where category_created_by= ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, userId);

        @SuppressWarnings("unchecked")
        List<Category> listUser = (List<Category>) query.list();

        if (listUser != null && !listUser.isEmpty()) {
            return listUser;
        }

        return null;

    }

}
