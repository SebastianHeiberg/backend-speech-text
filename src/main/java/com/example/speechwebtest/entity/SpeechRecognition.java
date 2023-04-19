package com.example.speechwebtest.entity;


import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class SpeechRecognition {
  // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
  private static String speechKey = System.getenv("SPEECH_KEY");
  private static String speechRegion = System.getenv("SPEECH_REGION");

  public static void recognizeFromMicrophone(String path) throws InterruptedException, ExecutionException {


    SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
    speechConfig.setSpeechRecognitionLanguage("en-US");
    AudioConfig audioConfig = AudioConfig.fromWavFileInput(path);

    SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
    SpeechRecognitionResult speechRecognitionResult = task.get();

    if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
      System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
    }
    else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
      System.out.println("NOMATCH: Speech could not be recognized.");
    }
    else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
      CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
      System.out.println("CANCELED: Reason=" + cancellation.getReason());

      if (cancellation.getReason() == CancellationReason.Error) {
        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
        System.out.println("CANCELED: Did you set the speech resource key and region values?");
      }
    }

  }
  }
