package com.disqo.assessment.notes.controllers;

import com.disqo.assessment.notes.models.Response;
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

    @GetMapping("/ping")
    public Response getPing() {
        return new Response("pong");
    }


}
