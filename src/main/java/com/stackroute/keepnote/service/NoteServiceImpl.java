package com.stackroute.keepnote.service;

import java.util.List;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
 public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	@Autowired
	private NoteDAO noteDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ReminderDAO reminderDAO;

	public NoteServiceImpl(NoteDAO noteDAO, CategoryDAO categoryDAO, ReminderDAO reminderDAO)
	{
		this.noteDAO = noteDAO;
		this.categoryDAO = categoryDAO;
		this.reminderDAO = reminderDAO;
	}


	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException
	{
		boolean noteCreated = false;

		Category category = null;
		Reminder reminder = null;

		try
		{
			if(note.getCategory()!=null)
			category = categoryDAO.getCategoryById(note.getCategory().getCategoryId());
		}
		catch (CategoryNotFoundException catNotFound)
		{
			catNotFound.printStackTrace();
		}

		try
		{
			if(note.getReminder() != null)
			reminder = reminderDAO.getReminderById(note.getReminder().getReminderId());
		}
		catch (ReminderNotFoundException remNotFound)
		{
			throw new ReminderNotFoundException("");
		}

		note.setCategory(category);
		note.setReminder(reminder);

		try {
			noteCreated = noteDAO.createNote(note);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		if(noteCreated)
		{
			noteCreated = true;
		}/*else
		{
			throw new ReminderNotFoundException("Reminder Not Found");
		}*/
		return noteCreated;

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) {
		return noteDAO.deleteNote(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return noteDAO.getAllNotesByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		return noteDAO.getNoteById(noteId);

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {

		Category category = null;
		if(note.getCategory() != null){
			categoryDAO.getCategoryById(note.getCategory().getCategoryId());
		}/*else {
			throw new CategoryNotFoundException("");
		}*/


		Reminder reminder = null;
		if(note.getReminder() != null) {
			reminderDAO.getReminderById(note.getReminder().getReminderId());
		}

		//Note note2 = noteDAO.getNoteById(id);

		Note note1 = noteDAO.getNoteById(id);
		if(note1 != null)
		{
			boolean noteUpdate = noteDAO.UpdateNote(note);
		}
		else
		{
			throw new NoteNotFoundException("");
		}

		return note;


	}

}
