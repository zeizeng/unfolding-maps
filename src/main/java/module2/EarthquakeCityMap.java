package module2;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static module2.ParseFeed.parseEarthquake;

/**
 * Description
 *
 * @author weizengflyinsky@gmail.com
 * @version 1.0
 * @date 2019/2/2117:56
 */
public class EarthquakeCityMap extends PApplet {

    private UnfoldingMap map;

    public void setup() {
        size(950, 600, OPENGL);
        map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.RoadProvider());
        map.zoomToLevel(5);
        List<PointFeature> bigEarthquack = parseEarthquake("E:\\ideaproject\\unfolding-maps\\data\\2.5_week.atom");
        List<Marker> markers = new ArrayList<>();
        for (PointFeature feature : bigEarthquack) {
            markers.add(new SimplePointMarker(feature.getLocation(), feature.getProperties()));
        }
        Color yellew = new Color(255, 255, 0);
        Color red = new Color(255, 0, 0);
        Pattern pattern = Pattern.compile("2\\d{3}");
        for (Marker marker : markers) {
            String date = marker.getStringProperty("date");
            Matcher matcher = pattern.matcher(date);
            if (matcher.find()) {
                marker.setColor(red.getRGB());
            } else {
                marker.setColor(yellew.getRGB());
            }
        }
        map.addMarkers(markers);
        MapUtils.createDefaultEventDispatcher(this, map);

    }

    public void draw() {
        background(220);
        map.draw();
    }
}
