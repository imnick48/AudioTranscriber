package com.audiotr.audiotranscriptor.Controller;

import com.audiotr.audiotranscriptor.Service.AudTService;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/transcribe/api")
public class AudTController {

    private final OpenAiAudioTranscriptionModel tmod;
    private final AudTService audTService;

    public AudTController(OpenAiAudioTranscriptionModel tmod, AudTService audTService) {
        this.tmod = tmod;
        this.audTService = audTService;
    }

    @PostMapping
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) throws IOException {
        return audTService.transcribeAudio(file, this.tmod);
    }
}
