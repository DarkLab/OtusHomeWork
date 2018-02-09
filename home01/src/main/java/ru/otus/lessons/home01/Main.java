package ru.otus.lessons.home01;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    private static String URL = "https://vk.com/feed";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        printString("\nMedia: (%d)", media.size());
        media.forEach(src -> {
            if (src.tagName().equals("img")) {
                printString(" * %s: <%s> %sx%s (%s)",
                        src.tagName(),
                        src.attr("abs:src"),
                        src.attr("width"),
                        src.attr("height"),
                        trim(src.attr("alt"), 20));
            } else {
                printString(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }
        });

        printString("\nImports: (%d)", imports.size());
        imports.forEach(link -> printString(" * %s <%s> (%s)",
                link.tagName(),
                link.attr("abs:href"),
                link.attr("rel")));

        printString("\nLinks: (%d)", links.size());
        links.forEach(link -> printString(" * a: <%s>  (%s)",
                link.attr("abs:href"),
                trim(link.text(), 35)));
    }

    private static void printString(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 1) + ".";
        } else {
            return s;
        }
    }
}
