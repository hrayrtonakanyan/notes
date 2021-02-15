package com.disqo.assessment.notes.controllers;

import com.disqo.assessment.notes.constants.RequestConstants;
import com.disqo.assessment.notes.models.misc.UserSession;
import com.disqo.assessment.notes.models.network.NoteDTO;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.services.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/15/21.
 * Time: 11:23 PM.
 */
@RestController
@RequestMapping("/v1")
public class NoteController {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(NoteController.class);
    // endregion

    // region Instance fields
    private NoteService noteService;
    // endregion

    // region Requests
    @GetMapping("/ping")
    public Response getPing() {
        return new Response("pong");
    }

    @PostMapping("/notes")
    public Response postNotes(@RequestAttribute(RequestConstants.USER_SESSION_ATTRIBUTE_NAME) final UserSession userSession,
                              @RequestBody final List<NoteDTO> noteDTOList) {
        long startTs = System.currentTimeMillis();
        logger.debug("[POST_NOTES - req] " + noteDTOList.toString());
        noteService.addNotes(userSession, noteDTOList);
        logger.debug("[POST_NOTES - resp] [" + (System.currentTimeMillis() - startTs) + "]");
        return new Response("areNotesAdded", true);
    }

    @PutMapping("/notes")
    public Response putNotes(@RequestAttribute(RequestConstants.USER_SESSION_ATTRIBUTE_NAME) final UserSession userSession,
                             @RequestBody final List<NoteDTO> noteDTOList) {
        long startTs = System.currentTimeMillis();
        logger.debug("[PUT_NOTES - req] " + noteDTOList.toString());
        noteService.updateNotes(userSession, noteDTOList);
        logger.debug("[PUT_NOTES - resp] [" + (System.currentTimeMillis() - startTs) + "]");
        return new Response("areNotesAdded", true);
    }
    // endregion

    // region Setters
    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }
    // endregion
}
