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
import java.lang.reflect.Field;
import java.text.Format;

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

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";

    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "oh mighty computer";

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
    public void getFile(String path, Callback callBack) throws IOException {
        File file = new File(path);
        Log.d("SpeechModule", "file size " +file.getTotalSpace()+" file path"+file.getAbsolutePath());
        callBack.invoke("Start Listening");

    }

    @ReactMethod
    public void startListening(String word, Callback callBack) throws IOException {
        ReactApplicationContext context = getReactApplicationContext();

        Assets assets = new Assets(context);
        File assetDir = assets.syncAssets();
        setupRecognizer(assetDir);

        switchSearch(DIGITS_SEARCH);

        callBack.invoke("Start Listening");

    }


    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

//                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)

                .getRecognizer();
        recognizer.addListener((Speech) this);

        /* In your application you might not need to add all those searches.
          They are added here for demonstration. You can leave just one.
         */
        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        // Create grammar-based search for selection between demos
        File menuGrammar = new File(assetsDir, "menu.gram");
        recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, "digits.gram");
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

        // Create language model search
        File languageModel = new File(assetsDir, "weather.dmp");
        recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

        // Phonetic search
        File phoneticModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);

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
//        Log.d("SpeechModule", "onPartialResult: " + toString(hypothesis));
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            int score =  hypothesis.getBestScore();
            Log.d("SpeechModule", "onPartialResult Text: " + text+" score: "+score);
        }

    }

    @Override
    public void onResult(Hypothesis hypothesis) {
//        Log.d("SpeechModule", "onResult: " + toString(hypothesis));

        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            int score =  hypothesis.getBestScore();
            Log.d("SpeechModule", "onResult Text: " + text+" score: "+score);
        }

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }

    public String toString(Hypothesis hypothesis) {
        StringBuilder result = new StringBuilder();

        if (hypothesis != null) {
            String newLine = System.getProperty("line.separator");

            result.append(hypothesis.getClass().getName());
            result.append(" Object {");
            result.append(newLine);

            //determine fields declared in this class only (no fields of superclass)
            Field[] fields = this.getClass().getDeclaredFields();

            //print field names paired with their values
            for (Field field : fields) {
                result.append("  ");
                try {
                    result.append(field.getName());
                    result.append(": ");
                    //requires access to private field:
                    result.append(field.get(this));
                } catch (IllegalAccessException ex) {
                    System.out.println(ex);
                }
                result.append(newLine);
            }
            result.append("}");

        } else {
            result.append("empty Object");

        }
        return result.toString();

    }
}