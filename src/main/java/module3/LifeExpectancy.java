package module3;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Description
 *
 * @author weizengflyinsky@gmail.com
 * @version 1.0
 * @date 2019/2/2516:37
 */
public class LifeExpectancy extends PApplet {

    UnfoldingMap map;
    Map<String, Float> lifeExpByCountry;
    List<Feature> countries = new ArrayList<>();
    List<Marker> markers = new ArrayList<>();


    public void setup() {
        size(800, 600, OPENGL);
        map = new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.RoadProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
        lifeExpByCountry = loadLifeExpectancyFromCSV("data/LifeExpectancyWorldBank.csv");
        countries = GeoJSONReader.loadData(this, "countries.geo.json");
        markers = MapUtils.createSimpleMarkers(countries);

        map.addMarkers(markers);
        shadeCountries();
    }

    public void draw() {
        map.draw();
    }

    private Map<String, Float> loadLifeExpectancyFromCSV(String filename) {
        Map<String, Float> lifeExpMap = new HashMap<>();
        String[] rows = loadStrings(filename);
        for (String row : rows) {
            String[] columns = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i=columns.length-1;i>3;i--){
                if(!columns[i].equals("..")) {
                    lifeExpMap.put(columns[3], Float.parseFloat(columns[i]));

                    // break once most recent data is found
                    break;
                }
            }
        }
        return lifeExpMap;
    }

    private void shadeCountries() {
        for (Marker marker : markers) {
            String countryId = marker.getId();
            if (lifeExpByCountry.containsKey(countryId)) {
                float lifeExp = lifeExpByCountry.get(countryId);
                int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
                marker.setColor(color(255 - colorLevel, 100, colorLevel));
            } else {
                marker.setColor(color(150, 150, 150));
            }
        }

    }
}
