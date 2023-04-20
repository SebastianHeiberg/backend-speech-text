package com.example.speechwebtest.api;

import com.example.speechwebtest.entity.SpeechRecognition;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RequestMapping("api/speak")
@RestController
@CrossOrigin
public class SpeakController {

  SpeechRecognition speechRecognition;

  public SpeakController(SpeechRecognition speechRecognition) {
    this.speechRecognition = speechRecognition;
  }

  @PostMapping
  public void handleAudio(@RequestBody byte[] audioData) throws Exception {

    //filen skal gemmes på skrivebord
    String filePath = "/Users/hejbe/Desktop/recording.mp3";

    //lav en ny fil
    File file = new File(filePath);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(audioData);
    } catch (IOException e) {
      throw e;
    }

  SpeechRecognition.recognizeFromMicrophone(filePath);

  }


}
