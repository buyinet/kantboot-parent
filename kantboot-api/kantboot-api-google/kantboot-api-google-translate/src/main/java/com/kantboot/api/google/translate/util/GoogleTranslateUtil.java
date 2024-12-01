package com.kantboot.api.google.translate.util;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3.*;
import com.kantboot.api.google.translate.exception.ApiTranslateException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GoogleTranslateUtil {

    public static String translateText(String projectId,String credJsonStr,String sourceLanguageCode , String targetLanguage, String text) {


        ByteArrayInputStream credStream = new ByteArrayInputStream(credJsonStr.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = null;
        TranslationServiceSettings settings = null;
        TranslationServiceClient client = null;
        try {
           credentials = GoogleCredentials.fromStream(credStream);
            // 使用凭证初始化TranslationServiceClient
            settings = TranslationServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();
            client = TranslationServiceClient.create(settings);
        } catch (Exception e) {
            throw ApiTranslateException.TRANSLATE_TEXT_ERROR;
        }
            // Supported Locations: `global`, [glossary location], or [model location]
            // Glossaries must be hosted in `us-central1`
            // Custom Models must use the same location as your model. (us-central1)
            LocationName parent = LocationName.of(projectId, "global");

            // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setSourceLanguageCode(sourceLanguageCode)
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(text)
                            .build();

            TranslateTextResponse response = client.translateText(request);

            List<Translation> translationsList = response.getTranslationsList();
            // 如果数组为空，说明翻译失败，返回###
            if(translationsList.isEmpty()) {
                return "###";
            }

            return translationsList.get(0).getTranslatedText();
    }

}
