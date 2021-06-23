package com.speech;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class Speech extends ReactContextBaseJavaModule implements RecognitionListener {

    private SpeechRecognizer recognizer = null;

    Speech(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Speech";
    }

    @ReactMethod
    public void createSpeechEvent(String name, String location, Callback callBack) {
        Log.d("SpeechModule", "Create event called with name: " + name
                + " and location: " + location);
        callBack.invoke("speech working");

    }

    @ReactMethod
    public void startListening(String word, Callback callBack) throws IOException {
        ReactApplicationContext context = getReactApplicationContext();

        Assets assets = new Assets(context);
        File assetDir = assets.syncAssets();
        setupRecognizer(assetDir);

        switchSearch("ant");

        callBack.invoke("Start Listening");

    }


    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)

                .getRecognizer();
        recognizer.addListener((Speech) this);

        /* In your application you might not need to add all those searches.
          They are added here for demonstration. You can leave just one.
         */


    }

    private void switchSearch(String searchName) {
        recognizer.stop();
        recognizer.startListening(searchName, 10000);
    }


    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Log.d("SpeechModule", "onResult: " + text);
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }


}