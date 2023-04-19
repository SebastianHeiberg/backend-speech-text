package com.example.speechwebtest.api;

import com.example.speechwebtest.entity.SpeechRecognition;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.util.concurrent.ExecutionException;

@RequestMapping("api/speak")
@RestController
@CrossOrigin
public class SpeakController {

  public SpeakController(SpeechRecognition speechRecognition) {
    this.speechRecognition = speechRecognition;
  }

  SpeechRecognition speechRecognition;



  @PostMapping
  public void handleAudio(@RequestBody byte[] audioData) throws IOException, ExecutionException, InterruptedException {

    String filePath = "/Users/hejbe/Desktop/recording.wav";

    File file = new File(filePath);

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(audioData);
    } catch (IOException e) {
      throw e;
    }

    SpeechRecognition.recognizeFromMicrophone(filePath);
  }
}