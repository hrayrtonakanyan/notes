package com.disqo.assessment.notes.models.network;

import com.disqo.assessment.notes.models.db.Note;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 3:10 AM.
 */
public class NoteDTO {

    private Long id;
    private String title;
    private String note;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.note = note.getNote();
    }

    public NoteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + note + '\'' +
                '}';
    }
}
