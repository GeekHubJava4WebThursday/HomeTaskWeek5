import source.URLSourceProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private static final String YANDEX_API_KEY = "trnsl.1.1.20151128T202424Z.ba24693e3e370fd2.f4409e090e433b470028aecc1b1caea821e34386";
    private static final String TRANSLATION_DIRECTION = "ru";
    private static final String TAG_TEXT = "<text>";
    private static final String CLOSE_TAG_TEXT = "</text>";

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
       URLSourceProvider urlTranslator = new URLSourceProvider();
        String url = prepareURL(original);

        String translateText = urlTranslator.load(url);
        return parseContent(translateText);
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
    private String parseContent(String content) {
        //TODO: implement me
        int copyFrom = content.indexOf(TAG_TEXT)+TAG_TEXT.length();
        int copyTo = content.indexOf(CLOSE_TAG_TEXT);

        return content.substring(copyFrom, copyTo);
    }

    /**
     * Encodes text that need to be translated to put it as URL parameter
     *
     * @param text to be translated
     * @return encoded text
     */
    private String encodeText(String text) {
        //TODO: implement me
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return text;
        }

    }
}
