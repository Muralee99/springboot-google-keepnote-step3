package com.stackroute.keepnote.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * The class "Note" will be acting as the data model for the Note Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name = "note")
public class Note {
    /*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). Out of these eight fields, the field noteId
	 * should be primary key and auto-generated. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of createdAt should not be
	 * accepted from the user but should be always initialized with the system date.
	 * annotate category and reminder field with @ManyToOne.
	 */

    public Note() {

    }

    public Note(int noteId, String noteTitle, String noteContent,
                String noteStatus, Date createdAt, Category category, Reminder reminder,
                String createdBy) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteStatus = noteStatus;
        this.noteCreatedAt = createdAt;
        this.createdBy = createdBy;
        this.category = category;
        this.reminder = reminder;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int noteId;

    @Column(name = "note_title")
    private String noteTitle;

    @Column(name = "note_content")
    private String noteContent;

    @Column(name = "note_status")
    private String noteStatus;

    @Column(name = "note_createdat")
    private Date noteCreatedAt;

    @Column(name = "note_createdby")
    private String createdBy;

/*	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="user_roles",
			joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "category_note",
            joinColumns = {@JoinColumn(name = "note_noteid", referencedColumnName = "note_id", nullable = true)},
            inverseJoinColumns = {@JoinColumn(name = "category_categoryid", referencedColumnName = "category_id", nullable = true)})
    private Category category;

    @ManyToOne
    @JoinTable(name = "reminder_note",
            joinColumns = {@JoinColumn(name = "note_noteid", referencedColumnName = "note_id", nullable = true)},
            inverseJoinColumns = {@JoinColumn(name = "reminder_reminderid", referencedColumnName = "reminder_id", nullable = true)})
    private Reminder reminder;


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setNoteStatus(String noteStatus) {
        this.noteStatus = noteStatus;
    }

    public void setNoteCreatedAt(Date noteCreatedAt) {
        this.noteCreatedAt = noteCreatedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getNoteStatus() {
        return noteStatus;
    }

    public Date getNoteCreatedAt() {
        return noteCreatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String toString() {
        return "Note [noteId=" + noteId + ", noteTitle=" + noteTitle + ", noteContent=" + noteContent + ", noteStatus="
                + noteStatus + ", noteCreatedAt=" + noteCreatedAt + ", category=" + category + ", reminder=" + reminder
                + ", createdBy=" + createdBy + "]";
    }

}
