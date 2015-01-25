/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintproject;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author supeUse
 */
public class Util {

    public static Effect highlight = new DropShadow(20, Color.GOLDENROD);

    public static String getClassString(Node forme) {
        if (forme instanceof Circle) {
            return "cercle";
        } else if (forme instanceof Rectangle) {
            return "rectangle";
        } else if (forme instanceof Line) {
            return "ligne";
        } else if (forme instanceof BlockForm) {
            return "blockform";
        } else if (forme instanceof Path) {
            return "freehand";
        } else if (forme instanceof Polygon) {
            return "triangle";
        } else {
            return "autre";
        }
    }

//    public static HashMap<Form, Long> inverseMap(HashMap<Long, Form> map) {
//        HashMap<Form, Long> inversedMap = new HashMap<>();
//        for (Map.Entry<Long, Form> entry : map.entrySet()) {
//            inversedMap.put(entry.getValue(), entry.getKey());
//        }
//        return inversedMap;
//    }

}
