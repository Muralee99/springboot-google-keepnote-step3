package com.stackroute.keepnote.dao;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class NoteDAOImpl implements NoteDAO {

    /*
     * Autowiring should be implemented for the SessionFactory.(Use
     * constructor-based autowiring.
     */
    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    public NoteDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	/*
     * Create a new note
	 */

    public boolean createNote(Note note) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean isNoteCreated = true;

        try {

            transaction = session.beginTransaction();
            note.setCategory(note.getCategory());
            note.setReminder(note.getReminder());
            //session.persist(note.getCategory());
            //session.persist(note.getReminder());
            session.saveOrUpdate(note);
            transaction.commit();
        } catch (Exception exception) {
            isNoteCreated = false;
            exception.printStackTrace();
        } finally {
            session.close();
        }

        return isNoteCreated;

    }

	/*
	 * Remove an existing note
	 */

    public boolean deleteNote(int noteId) {

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        boolean isNoteDeleted = true;
        Note note = new Note();
        note.setNoteId(noteId);
        try {
            transaction = session.beginTransaction();
            //session.delete(note.getCategory().getCategoryId());
            //session.delete(note.getReminder().getReminderId());
            session.delete(note);
            transaction.commit();
        } catch (Exception exception) {
            isNoteDeleted = false;
        } finally {
            session.close();
        }
        return isNoteDeleted;
    }

	/*
	 * Retrieve details of all notes by userId
	 */

    public List<Note> getAllNotesByUserId(String userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        String hql = "from Note where note_createdby= ?";
        Query query = session.createQuery(hql).setParameter(0, userId);

        @SuppressWarnings("unchecked")
        List<Note> listNotes = (List<Note>) query.list();

        if (listNotes != null && !listNotes.isEmpty()) {
            return listNotes;
        }
        session.close();
        return null;
    }

	/*
	 * Retrieve details of a specific note
	 */

    public Note getNoteById(int noteId) throws NoteNotFoundException {

		/*DateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
		Date date = format.parse(myString);*/

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String input = args.length == 0 ? "1818-11-11" : args[0];
        Date t = null;
        Session session = sessionFactory.openSession();
        String hql = "from Note where note_id= ?";
        Query query = session.createQuery(hql).setParameter(0, noteId);

        @SuppressWarnings("unchecked")
        List<Note> note = (List<Note>) query.list();

        if (note != null && !note.isEmpty()) {
            session.close();
            try {
                t = ft.parse(note.get(0).getNoteCreatedAt().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            note.get(0).setNoteCreatedAt(t);
            Note note2 = (note.get(0));
            return note2;
        } else {
            session.close();
            throw new NoteNotFoundException("");
        }


        //return null;

    }

	/*
	 * Update an existing note
	 */

    public boolean UpdateNote(Note note) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        boolean isNoteUpdated = true;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(note);
            transaction.commit();
        } catch (Exception exception) {
            isNoteUpdated = false;
        } finally {
            session.close();
        }
        return isNoteUpdated;


    }

}
