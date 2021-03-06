import com.sun.deploy.util.ArrayUtil;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.batik.dom.AbstractParentNode;
import org.apache.batik.dom.svg.*;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGMPathElement;
import org.w3c.dom.svg.SVGPoint;
import org.xml.sax.SAXException;
import processing.data.XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by surface on 15/07/2016.
 */
public class XMLer {

//    public static SVGPoint[] pointsFromSVG(String fileName) {
//
//        SVGOMPathElement theElement;
//        //theElement.
//
//        Document doc = null;
//        try {
//            String parser = XMLResourceDescriptor.getXMLParserClassName();
//            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
//            URI file = new File("file.svg").toURI();
//            System.out.println("uri creation happened");
//            String uri = file.toString();
//            doc = f.createDocument(uri);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            System.out.println("your'e farrrked");
//            return null;
//        }
//        NodeList h = (doc.getElementsByTagName("path"));
//        System.out.println(h.item(0));
//        SVGOMPathElement thePath = (SVGOMPathElement)h.item(0);
//        //System.out.println(thePath.getPathLength());
//        SVGPoint[] points = new SVGPoint[1000];
//        for(int i=0; i<1000; i++) {
//            System.out.println(thePath.getPointAtLength(i));
//            points[i] = thePath.getPointAtLength(((float)(i)));
//            System.out.println(points[i].getX() + ", " + points[1].getY());
//        }
//
//        return null;//points;
//    }

    @Deprecated float[] pointsFromXML(String fileName) {
        float[] points = null;
        File opened = new File(fileName);
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(opened);
            NodeList pathList = doc.getElementsByTagName("path");
            for (int i = 0; i < pathList.getLength(); i++) {
                org.w3c.dom.Node p = pathList.item(i);
                if (p.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element path = (Element) p;
                    String d = path.getAttribute("d");
//                    System.out.println(d);
                    String[] pointsets = d.split("[^\\d|.|z\\-\\.]");
                    float[] allPointsInThisPath = new float[pointsets.length];
                    int jDest = -1;
                    for (int jSource = 0; jSource < pointsets.length; jSource++) {
                        Scanner sc = new Scanner(pointsets[jSource]);
                        while (sc.hasNextFloat()) {
                            float now = sc.nextFloat();
                            if (now != 0) {
                                jDest++;
                                allPointsInThisPath[jDest] = now;
                                continue;
                            }


                        }
                        if (pointsets[jSource].equalsIgnoreCase("z")) {
//                            System.out.println("pointset[j] equaledignorecase z");
                            allPointsInThisPath[jDest + 1] = allPointsInThisPath[0];
                            allPointsInThisPath[jDest + 2] = allPointsInThisPath[1];
                        }

//                        System.out.println(pointsets[jSource]);

                    }
                    int end = 0;

                    for (int j = 0; j < allPointsInThisPath.length; j++) {
                        if (allPointsInThisPath[j] == 0) {
                            end = j;
                            break;
                        }
                    }
                    float[] clean = new float[end];

//                    System.out.println("\nfinal array ");
                    for (int j = 0; j < clean.length; j++) {
                        clean[j] = allPointsInThisPath[j];
//                        System.out.println(clean[j]);

                    }
//                    System.out.println();
                    points = clean;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        }
        return points;
    }

    float[] pointsFromXMLProcessing(String fileName) {

        float[] CoOrds = null;

        XML all = Main.instance.loadXML(fileName);
        XML[] paths = all.getChildren("path");

        for (int i = 0; i < paths.length; i++) {
            String thisPath = paths[i].getString("d");
            char[] letters = new char[thisPath.length()];
            for (int j = 0; j < thisPath.length(); j++) {
                char q = thisPath.charAt(j);
                if (q != '0' & q != '1' & q != '2' & q != '3' & q != '4' & q != '5' & q != '6' & q != '7' & q != '8' & q != '9' & q != '.') {
                    letters[j] = ' ';
                } else {
                    letters[j] = q;
                }
            }
            String clean = new String(letters);

            String[] numbers = clean.split(",");
            for (int j = 0; j < numbers.length; j++) {
                //println(numbers[j]);
                String[] onePath = numbers[j].split(" ");
                CoOrds = new float[onePath.length];

                int k = 0;
                int k1 = 0;
                while (k < onePath.length) {
                    float now = 0;
                    try {
                        now = Float.parseFloat(onePath[k]);
                    } catch (NumberFormatException e) {
                        k++;
                        continue;
                    }
                    //println(now);
                    if (now == now) {
                        CoOrds[k1] = now;
                        k1++;
                    }
                    //println(onePath[k] + "\t\t\t" + float(onePath[k]) + "\t" + float(onePath[k+1]));
                    k++;
                }
                for (int l = 0; l < CoOrds.length; l += 2) {
                    System.out.println(CoOrds[l] + ",\t" + CoOrds[l + 1]);
                }
            }
        }
        return CoOrds;
    }
}
