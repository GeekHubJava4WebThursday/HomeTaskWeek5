package com.shs;

import com.shs.source.URLSourceProvider;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utilities for translating texts to russian language.<br/>
 * Uses Yandex Translate API, more information at <a href="http://api.yandex.ru/translate/">http://api.yandex.ru/translate/</a><br/>
 * Depends on {@link URLSourceProvider} for accessing Yandex com.shs.Translator API service
 */
public class Translator {
    /**
     * Yandex Translate API key could be obtained at <a href="http://api.yandex.ru/key/form.xml?service=trnsl">http://api.yandex.ru/key/form.xml?service=trnsl</a>
     * to do that you have to be authorized.
     */
    private static final String YANDEX_API_KEY = "trnsl.1.1.20151113T183502Z.7af0274609dec1a3.c71af9a3d64912197203c1aaeb9687ffb079dc56";
    private static final String TRANSLATION_DIRECTION = "en-ru";
    private URLSourceProvider urlSourceProvider;

    public Translator(URLSourceProvider urlSourceProvider) {
        this.urlSourceProvider = urlSourceProvider;
    }

    /**
     * Translates text to russian language
     *
     * @param original text to translate
     * @return translated text
     * @throws IOException
     */
    public String translate(String original) throws IOException {
        String url = prepareURL(encodeText(original));
        return parseContent(urlSourceProvider.load(url));
    }

    /**
     * Prepares URL to invoke Yandex Translate API service for specified text
     *
     * @param text to translate
     * @return url for translation specified text
     */
    private String prepareURL(String text) {
        return "https://translate.yandex.net/api/v1.5/tr/translate?key=" + YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" + TRANSLATION_DIRECTION;
    }

    /**
     * Parses content returned by Yandex Translate API service. Removes all tags and system texts. Keeps only translated text.
     *
     * @param content that was received from Yandex Translate API by invoking prepared URL
     * @return translated text
     */
    public String parseContent(String content) {
        final Pattern pattern = Pattern.compile("<text>(.+?)</text>");
        final Matcher matcher = pattern.matcher(content);
        matcher.find();
        return matcher.group(1);
    }

    /**
     * Encodes text that need to be translated to put it as URL parameter
     *
     * @param text to be translated
     * @return encoded text
     */
    public String encodeText(String text) {
        return text.replaceAll(" ", "%20");
    }
}
