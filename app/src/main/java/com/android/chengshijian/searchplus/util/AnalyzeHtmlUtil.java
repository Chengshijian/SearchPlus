package com.android.chengshijian.searchplus.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 分析html的工具类
 *
 * Created by chengshijian on 2017/2/1.
 */

public class AnalyzeHtmlUtil {
    public static Document formatHtmlString2Document(String htmlString) {
        return Jsoup.parse(htmlString);
    }

    public static List<String> formatHtmlByClass(Document document, String clas) {
        List<String> texts = new ArrayList<>();
        Elements content = document.getElementsByClass(clas);
        addElementsToList(texts, content);
        return texts;
    }

    public static List<String> formatHtmlBySelect(Document document) {
        List<String> texts = new ArrayList<>();
        Elements content = document.select("td[height=\"36\"]");
        addElementsToList(texts, content);
        return texts;
    }

    private static void addElementsToList(List<String> list, Elements elements) {
        for (Element element : elements) {
            list.add(element.text());
        }
    }
}
