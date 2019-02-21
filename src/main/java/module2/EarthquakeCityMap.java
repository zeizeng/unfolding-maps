package module2;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

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
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

    }

    public void draw() {
        background(220);
        map.draw();
    }
}
