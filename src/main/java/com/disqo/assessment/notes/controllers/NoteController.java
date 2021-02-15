package com.disqo.assessment.notes.controllers;

import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.services.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // endregion

    // region Setters
    @Autowired
    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }
    // endregion
}
