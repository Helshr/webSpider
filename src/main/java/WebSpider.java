import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebSpider {

    private String raw;
    private Document doc;

    public void get(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            this.doc =  doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Element getByClass(String className) {
        Element container = doc.getElementsByClass(className).first();
        return container;
    }

    public Elements getByClassAll(String className) {
        Elements container = doc.getElementsByClass(className);
        return container;
    }

    public Element getById(String Id) {
        Element container = doc.getElementById(Id);
        return container;
    }

    public void douban(){
        String baseUrl = "https://movie.douban.com/top250?start=";
//                URL url = new URL(null,urlStr,new sun.net.www.protocol.https.Handler())
        for (int i = 0; i < 10; i++) {
            int index = i * 25;
            String url = baseUrl + index + "&filter=";
            System.out.println(url);
            try {
                URL u = new URL(null, url, new sun.net.www.protocol.https.Handler());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            get(url);
            Elements items = getByClassAll("item");
            for (Element item: items) {
                String ranke = item.getElementsByTag("em").text();
                String name = item.getElementsByClass("title").text();
                String grade = item.getElementsByClass("rating_num").text();
                String data = ranke + ": " + name + ": " + grade + "\n";
                try {
                    Utils.writeFile("douban.txt", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        WebSpider ws = new WebSpider();
        ws.douban();
    }
}