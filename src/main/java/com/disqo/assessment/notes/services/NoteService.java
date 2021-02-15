package com.disqo.assessment.notes.services;

import com.disqo.assessment.notes.exceptions.InvalidRequestException;
import com.disqo.assessment.notes.models.db.Note;
import com.disqo.assessment.notes.models.misc.UserSession;
import com.disqo.assessment.notes.models.network.NoteDTO;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.repositories.NoteRepository;
import com.disqo.assessment.notes.utils.NotesProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/15/21.
 * Time: 11:40 PM.
 */
@Service
public class NoteService {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(NoteService.class);
    // endregion

    // region Instance fields
    private NoteRepository noteRepository;
    // endregion

    // region Business logic
    public void addNotes(UserSession userSession, List<NoteDTO> noteDTOList) {

        List<Note> noteList = new ArrayList<>();
        for (NoteDTO noteDTO : noteDTOList) {
            if (noteDTO.getTitle().length() > NotesProperties.getNoteTitleMaxLength()) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_TITLE_IS_TO_LONG");
            }
            if (noteDTO.getNote().length() > NotesProperties.getNoteBodyMaxLength()) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_BODY_IS_TO_LONG");
            }
            noteList.add(new Note(noteDTO, userSession.getUserId()));
        }
        noteRepository.saveAll(noteList);
    }

    public void updateNotes(UserSession userSession, List<NoteDTO> noteDTOList) {

        Map<Long, NoteDTO> noteDTOMap = new HashMap<>();
        for (NoteDTO noteDTO : noteDTOList) {
            if (noteDTO.getId() == null || noteDTO.getId() < 1) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_ID_NOT_FOUND_OR_WRONG");
            }
            if (noteDTO.getTitle().length() > NotesProperties.getNoteTitleMaxLength()) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_TITLE_IS_TO_LONG");
            }
            if (noteDTO.getNote().length() > NotesProperties.getNoteBodyMaxLength()) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_BODY_IS_TO_LONG");
            }
            noteDTOMap.put(noteDTO.getId(), noteDTO);
        }

        List<Note> noteList = noteRepository.findAllByIdIn(noteDTOMap.keySet());
        for (Note note : noteList) {
            NoteDTO noteDTO = noteDTOMap.get(note.getId());
            note.setTitle(noteDTO.getTitle());
            note.setNote(noteDTO.getNote());
            note.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
        noteRepository.saveAll(noteList);
    }
    // endregion

    // region Setters
    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    // endregion
}
