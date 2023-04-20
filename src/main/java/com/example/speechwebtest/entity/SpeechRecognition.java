package com.example.speechwebtest.entity;


import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class SpeechRecognition {
  // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
  private static String speechKey = System.getenv("SPEECH_KEY");
  private static String speechRegion = System.getenv("SPEECH_REGION");

  public static void recognizeFromMicrophone(String path) throws InterruptedException, ExecutionException, FileNotFoundException {


    SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
    speechConfig.setSpeechRecognitionLanguage("en-US");

    //importeret fra tutorial til at hente lokalt placeret lydfil
    String filePath = path;
    PullAudioInputStream pullAudio = AudioInputStream.createPullStream(new BinaryAudioStreamReader(filePath),
        AudioStreamFormat.getCompressedFormat(AudioStreamContainerFormat.MP3));
    AudioConfig audioConfig = AudioConfig.fromStreamInput(pullAudio);

    SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);


    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
    SpeechRecognitionResult speechRecognitionResult = task.get();

    if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
      System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
    } else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
      System.out.println("NOMATCH: Speech could not be recognized.");
    } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
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
