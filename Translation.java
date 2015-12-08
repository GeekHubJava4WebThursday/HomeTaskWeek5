package com.company;

import java.io.IOException;
import com.company.src.URLSourceProvider;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by InnaBakum on 01.12.2015.
 */
public class Translation {
    private URLSourceProvider urlSourceProvider;

    private static final String YANDEX_API_KEY = "trnsl.1.1.20151208T101945Z.342811abacdd60a7.644872b5cb726286c7b5fbac3ecccc6e43df4a98";
    private static final String TRANSLATION_DIRECTION = "ru";

    public Translation(URLSourceProvider urlSourceProvider) {
        this.urlSourceProvider = urlSourceProvider;
    }


    public String translate(String original) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (original.contains(".")) {
            for (String line : original.split("[.]")) {
                stringBuilder.append(parseContent(urlSourceProvider.load(prepareURL(line)))).append(".");
            }
        } else {
            stringBuilder.append(parseContent(urlSourceProvider.load(prepareURL(original))));
        }
        return stringBuilder.toString();
    }


    private String prepareURL(String text) {
        return "https://translate.yandex.net/api/v1.5/tr/translate?key=" +
                YANDEX_API_KEY + "&text=" + encodeText(text) + "&lang=" +
                TRANSLATION_DIRECTION;
    }


    private String parseContent(String content) {
        if (content.equals("CONNECTION FAIL")) {
            return "[ lost text ]";
        }
        String text = content.substring(content.indexOf("<text>") +6, content.indexOf("</text>"));
        //text = text.replace("**", "\n");
        return text;
    }


    private String encodeText(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
