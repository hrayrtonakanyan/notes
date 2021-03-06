package com.disqo.assessment.notes.services;

import com.disqo.assessment.notes.exceptions.InvalidRequestException;
import com.disqo.assessment.notes.models.db.Note;
import com.disqo.assessment.notes.models.misc.UserSession;
import com.disqo.assessment.notes.models.network.NoteDTO;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.repositories.NoteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Value("${note.title.max.length}")
    private int noteTitleMaxLength;
    @Value("${note.body.max.length}")
    private int noteBodyMaxLength;

    private NoteRepository noteRepository;
    // endregion

    // region Business logic
    public void addNotes(UserSession userSession, List<NoteDTO> noteDTOList) {

        List<Note> noteList = new ArrayList<>();
        for (NoteDTO noteDTO : noteDTOList) {
            if (noteDTO.getTitle().isBlank() || noteDTO.getTitle().length() > noteTitleMaxLength) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_TITLE_IS_TO_LONG");
            }
            if (noteDTO.getNote().length() > noteBodyMaxLength) {
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
            if (noteDTO.getTitle().isBlank() || noteDTO.getTitle().length() > noteTitleMaxLength) {
                throw new InvalidRequestException(Response.ErrorType.PARAMETER_MISMATCH, "NOTE_TITLE_IS_TO_LONG");
            }
            if (noteDTO.getNote().length() > noteBodyMaxLength) {
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

    public List<NoteDTO> getNotes(UserSession userSession, int offset, int limit) {

        Pageable pageable = PageRequest.of(offset, limit);
        List<Note> noteList = noteRepository.findAllByUserIdOrderByCreatedAt(userSession.getUserId(), pageable);

        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (Note note : noteList) {
            noteDTOList.add(new NoteDTO(note));
        }
        return noteDTOList;
    }

    @Transactional
    public void deleteNotes(UserSession userSession, List<Long> noteIdList) {
        noteRepository.deleteAllByUserIdAndIdIn(userSession.getUserId(), noteIdList);
    }
    // endregion

    // region Setters
    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    // endregion
}
