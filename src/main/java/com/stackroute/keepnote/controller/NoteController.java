package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {

    /*
     * Autowiring should be implemented for the NoteService. (Use Constructor-based
     * autowiring) Please note that we should not create any object using the new
     * keyword
     */
    @Autowired
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /*
     * Define a handler method which will create a specific note by reading the
     * Serialized object from request body and save the note details in a Note table
     * in the database.Handle ReminderNotFoundException and
     * CategoryNotFoundException as well. please note that the loggedIn userID
     * should be taken as the createdBy for the note.This handler method should
     * return any one of the status messages basis on different situations: 1.
     * 201(CREATED) - If the note created successfully. 2. 409(CONFLICT) - If the
     * noteId conflicts with any existing user3. 401(UNAUTHORIZED) - If the user
     * trying to perform the action has not logged in.
     *
     * This handler method should map to the URL "/note" using HTTP POST method
     */
    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public ResponseEntity<Note> createNote(@RequestBody Note note, HttpSession session) {
        boolean isCreated = false;
        boolean isValidUser = validateUser(session);

        try {
            if (isValidUser) {
                isCreated = noteService.createNote(note);
            } else {
                return new ResponseEntity<Note>(HttpStatus.UNAUTHORIZED);
            }
        } catch (ReminderNotFoundException e) {
            e.printStackTrace();
        } catch (CategoryNotFoundException e) {
            e.printStackTrace();
        }

        if (isCreated) {
            return new ResponseEntity<Note>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Note>(HttpStatus.CONFLICT);
        }
    }

    /*
     * Define a handler method which will delete a note from a database.
     *
     * This handler method should return any one of the status messages basis on
     * different situations: 1. 200(OK) - If the note deleted successfully from
     * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
     * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
     * logged in.
     *
     * This handler method should map to the URL "/note/{id}" using HTTP Delete
     * method" where "id" should be replaced by a valid noteId without {}
     */
    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Note> deleteNote(@PathVariable int id, HttpSession session) {
        boolean isNoteDeleted = false;
        boolean isValidUser = validateUser(session);

        if (isValidUser) {
            isNoteDeleted = noteService.deleteNote(id);
        } else {
            return new ResponseEntity<Note>(HttpStatus.UNAUTHORIZED);
        }


        if (isNoteDeleted) {
            return new ResponseEntity<Note>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Define a handler method which will update a specific note by reading the
     * Serialized object from request body and save the updated note details in a
     * note table in database handle ReminderNotFoundException,
     * NoteNotFoundException, CategoryNotFoundException as well. please note that
     * the loggedIn userID should be taken as the createdBy for the note. This
     * handler method should return any one of the status messages basis on
     * different situations: 1. 200(OK) - If the note updated successfully. 2.
     * 404(NOT FOUND) - If the note with specified noteId is not found. 3.
     * 401(UNAUTHORIZED) - If the user trying to perform the action has not logged
     * in.
     *
     * This handler method should map to the URL "/note/{id}" using HTTP PUT method.
     */
    @RequestMapping(value = "/note/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Note> updateNote(@PathVariable int id, @RequestBody Note note, HttpSession session) {
        Note updatedNote = null;
        boolean isValidUser = validateUser(session);

        try {
            if (isValidUser) {
                updatedNote = noteService.updateNote(note, id);
            } else {
                return new ResponseEntity<Note>(HttpStatus.UNAUTHORIZED);
            }
        } catch (ReminderNotFoundException e) {
            e.printStackTrace();
        } catch (CategoryNotFoundException e) {
            e.printStackTrace();
        } catch (NoteNotFoundException nnf) {
            nnf.printStackTrace();
        }

        if (updatedNote != null) {
            return new ResponseEntity<Note>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Define a handler method which will get us the notes by a userId.
     *
     * This handler method should return any one of the status messages basis on
     * different situations: 1. 200(OK) - If the note found successfully. 2.
     * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
     * in.
     *
     *
     * This handler method should map to the URL "/note" using HTTP GET method
     */
    @RequestMapping(value = "/note", method = RequestMethod.GET)
    public ResponseEntity<List<Note>> getNotesByUserId(HttpSession session) {
        List<Note> notes = null;

        boolean isValidUser = validateUser(session);

        if (isValidUser) {
            notes = noteService.getAllNotesByUserId(session.getAttribute("loggedInUserId").toString());
        } else {
            return new ResponseEntity<List<Note>>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);

    }

    private boolean validateUser(HttpSession session) {
        if (session.getAttribute("loggedInUserId") != null) {
            return true;
        }
        return false;
    }

}
