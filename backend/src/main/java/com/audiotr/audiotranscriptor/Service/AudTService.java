package com.audiotr.audiotranscriptor.Service;

import java.io.File;
import java.io.IOException;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudTService {
    public ResponseEntity<String> transcribeAudio(MultipartFile file, OpenAiAudioTranscriptionModel amod) throws IOException{
        File tempFile = File.createTempFile("audio",".wav");
        file.transferTo(tempFile);

        OpenAiAudioTranscriptionOptions to = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .language("en")
                .temperature(0.4f)
                .build();
        FileSystemResource fr=new FileSystemResource(tempFile);
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(fr, to);
        AudioTranscriptionResponse response = amod.call(transcriptionRequest);
        tempFile.delete();
        return new ResponseEntity<>(response.getResult().getOutput(), HttpStatus.OK);
    }
}
