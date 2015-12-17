import source.URLSourceProvider;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String YANDEX_API_KEY = "trnsl.1.1.20151217T124325Z.fd46fa1b3b12d766.ea64e2851a4a13481c076a1436a043d2d6b226c6";
    private static final String TRANSLATION_DIRECTION = "en-ru";

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
        String translateText = urlSourceProvider.load(prepareURL(original));
        return parseContent(translateText);
//        return translateText;
    }

    /**
     * Prepares URL to invoke Yandex Translate API service for specified text
     * @param text to translate
     * @return url for translation specified text
     */
    private String prepareURL(String text) {
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" + TRANSLATION_DIRECTION;
    }

    /**
     * Parses content returned by Yandex Translate API service. Removes all tags and system texts. Keeps only translated text.
     * @param content that was received from Yandex Translate API by invoking prepared URL
     * @return translated text
     */
    private String parseContent(String content) {
        Pattern pattern = Pattern.compile("text\":\\[\"(.*)\"\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        matcher.find();
        return matcher.group(1);
    }

    /**
     * Encodes text that need to be translated to put it as URL parameter
     * @param text to be translated
     * @return encoded text
     */
    private String encodeText(String text) {

        return text
                .replace("!", "%21")
                .replace("\"", "%22")
                .replace("#", "%23")
                .replace("%", "%25")
                .replace("&", "%26")
                .replace("\'", "%27")
                .replace("*", "%2a")
                .replace(",", "%2c")
                .replace(":", "%3a")
                .replace(";", "%3b")
                .replace("<", "%3c")
                .replace("=", "%3d")
                .replace(">", "%3e")
                .replace("?", "%3f")
                .replace("[", "%5b")
                .replace("]", "%5d")
                .replace("^", "%5e")
                .replace("`", "%60")
                .replace("{", "%7b")
                .replace("|", "%7c")
                .replace("}", "%7d")
                .replace(" ", "%20");
    }
}
