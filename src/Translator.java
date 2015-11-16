import source.SourceProvider;
import source.URLSourceProvider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Provides utilities for translating texts to russian language.<br/>
 * Uses Yandex Translate API, more information at <a href="http://api.yandex.ru/translate/">http://api.yandex.ru/translate/</a><br/>
 * Depends on {@link URLSourceProvider} for accessing Yandex Translator API service
 */
public class Translator {

    private URLSourceProvider urlSourceProvider;
    /**
     * Yandex Translate API key could be obtained at <a href="http://api.yandex.ru/key/form.xml?service=trnsl">http://api.yandex.ru/key/form.xml?service=trnsl</a>
     * to do that you have to be authorized.
     */
    private static final String YANDEX_API_KEY = "trnsl.1.1.20151115T083103Z.69b1f7ef5fe6f797.de4154f1860b4fbb75634132a8f365685a0b2e5b";
    private static final String TRANSLATION_DIRECTION = "ru";

    public Translator(URLSourceProvider urlSourceProvider) {
        this.urlSourceProvider = urlSourceProvider;
    }

    /**
     * Translates text to russian language
     * @param original text to translate
     * @return translated text
     * @throws IOException
     */
    public String translate(String original) throws IOException {
        String response = urlSourceProvider.load(prepareURL(original));
        return parseContent(response);
    }

    /**
     * Prepares URL to invoke Yandex Translate API service for specified text
     * @param text to translate
     * @return url for translation specified text
     */
    private String prepareURL(String text) throws UnsupportedEncodingException {
        return "https://translate.yandex.net/api/v1.5/tr/translate?key=" +
                YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" + TRANSLATION_DIRECTION;
    }

    /**
     * Parses content returned by Yandex Translate API service. Removes all tags and system texts. Keeps only translated text.
     * @param content that was received from Yandex Translate API by invoking prepared URL
     * @return translated text
     */
    private String parseContent(String content) {
        int startIndex = content.indexOf("<text>") + 7;
        int endIndex = content.indexOf("</text>");
        return content.substring(startIndex, endIndex);
    }

    //private String get

    /**
     * Encodes text that need to be translated to put it as URL parameter
     * @param text to be translated
     * @return encoded text
     */
    private String encodeText(String text) throws UnsupportedEncodingException {
        return text.replaceAll(" ", "+");
    }
}
