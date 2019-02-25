package module2;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author weizengflyinsky@gmail.com
 * @version 1.0
 * @date 2019/2/2217:07
 */
public class ParseFeed {

    public static List<PointFeature> parseEarthquake(String fileName) {
        List<PointFeature> pointFeatures = new ArrayList<>();
        File file = new File(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(stringBuilder.toString());
        Elements entries = document.select("entry");
        for (Element entery : entries) {
            PointFeature pointFeature = new PointFeature();
            Elements titles = entery.select("title");
            if (titles != null) {
                pointFeature.addProperty("title", titles.text());
            }
            Elements updateds = entery.select("updated");
            if (updateds != null) {
                pointFeature.addProperty("date", updateds.text());
            }
            Elements locas = entery.getElementsByTag("georss:point");
            if (locas != null) {
                String location = locas.text().replaceAll("\n", "").trim();
                if (location.contains(" ")) {
                    String[] points = location.split(" ");
                    if (points.length == 2) {
                        Location loc = new Location(Float.parseFloat(points[0]), Float.parseFloat(points[1]));
                        pointFeature.setLocation(loc);
                    }
                }
            }
            Elements category = entery.select("category");
            for (Element categ : category) {
                if (categ.attr("label").equals("Magnitude")) {
                    pointFeature.addProperty("magnitude", categ.attr("term"));
                    break;
                }
            }
            pointFeatures.add(pointFeature);
        }
        return pointFeatures;
    }

    public static void main(String[] args) {

    }

}
