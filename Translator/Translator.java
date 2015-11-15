import source.URLSourceProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utilities for translating texts to russian language.<br/>
 * Uses Yandex Translate API, more information at <a href="http://api.yandex.ru/translate/">http://api.yandex.ru/translate/</a><br/>
 * Depends on {@link URLSourceProvider} for accessing Yandex Translator API service
 */
public class Translator {
    private static final Pattern TRANSLATED_TEXT_PATTERN = Pattern.compile(".*<text>(.*)</text>.*", Pattern.DOTALL);
    private static final Pattern ERROR_CODE_PATTERN = Pattern.compile("[\\s\\S]* code=\"(\\d{3})\"[\\s\\S]*");

    private URLSourceProvider urlSourceProvider;
    /**
     * Yandex Translate API key could be obtained at <a href="http://api.yandex.ru/key/form.xml?service=trnsl">http://api.yandex.ru/key/form.xml?service=trnsl</a>
     * to do that you have to be authorized.
     */
    private static final String YANDEX_API_KEY = "trnsl.1.1.20151114T084702Z.cf2d4027d4157872.424577e3cb6a60d0c74be55e4a42d79f42de67b6";
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
        return decodeText(parseContent(urlSourceProvider.load(prepareURL(original))));
    }

    /**
     * Prepares URL to invoke Yandex Translate API service for specified text
     * @param text to translate
     * @return url for translation specified text
     */
    private String prepareURL(String text) {
        return "https://translate.yandex.net/api/v1.5/tr/translate?key=" + YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" + TRANSLATION_DIRECTION;
    }

    /**
     * Parses content returned by Yandex Translate API service. Removes all tags and system texts. Keeps only translated text.
     * @param content that was received from Yandex Translate API by invoking prepared URL
     * @return translated text
     */
    private String parseContent(String content) throws IOException {
        Matcher errorCodeMatcher = ERROR_CODE_PATTERN.matcher(content);
        if (! errorCodeMatcher.matches()) {
            throw new IOException("Wrong server response");
        }

        Integer errorCode = Integer.parseInt(errorCodeMatcher.group(1));
        if (errorCode != 200) {
            throw new IOException(getErrorMessageByErrorCode(errorCode));
        }

        Matcher translatedTextMatcher = TRANSLATED_TEXT_PATTERN.matcher(content);
        if (translatedTextMatcher.matches()) {
            return translatedTextMatcher.group(1);
        } else {
            throw new IOException("Wrong server response");
        }
    }

    /**
     * Returns error message corresponding to https://tech.yandex.ru/translate/doc/dg/reference/translate-docpage/
     * @param errorCode code of error
     * @return error message
     */
    private String getErrorMessageByErrorCode(Integer errorCode) {
        switch (errorCode) {
            case 200: return "The operation completed successfully";
            case 401: return "Wrong API key";
            case 402: return "The API key is blocked";
            case 403: return "Exceeded the daily limit on the number of requests";
            case 404: return "Exceeded the daily limit on the amount of translated text";
            case 413: return "Exceeded the maximum allowed text size";
            case 422: return "The text could not be parsed";
            case 501: return "The specified translation direction is not supported";
            default:
                throw new IllegalArgumentException("Wrong error code in server response");
        }
    }

    /**
     * Encodes text that need to be translated to put it as URL parameter
     * @param text to be translated
     * @return encoded text
     */
    private String encodeText(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decodes text that has been translated for printing in console
     * @param text text for decode
     * @return normal text
     */
    private String decodeText(String text) {
        text = text
                .replace("&lt;","<")
                .replace("&gt;",">")
                .replace("&amp;","&");

        return text;
    }

}
