import processing.data.XML;

/**
 * Created by surface on 15/07/2016.
 */
public class XMLer {
    float[] pointsFromXML(String fileName) {

        float[] CoOrds=null;

        XML all = Main.instance.loadXML(fileName);
        XML[] paths = all.getChildren("path");

        for(int i=0; i<paths.length; i++) {
            String thisPath = paths[i].getString("d");
            char[] letters = new char[thisPath.length()];
            for(int j=0;j<thisPath.length();j++) {
                char q = thisPath.charAt(j);
                if(q!='0' & q!='1' & q!='2' & q!='3' & q!='4' & q!='5' & q!='6' & q!='7' & q!='8' & q!='9' & q!='.') {
                    letters[j]=' ';
                }
                else {
                    letters[j] = q;
                }
            }
            String clean = new String(letters);

            String[] numbers = clean.split(",");
            for(int j=0; j<numbers.length; j++) {
                //println(numbers[j]);
                String[] onePath = numbers[j].split(" ");
                CoOrds = new float[onePath.length];

                int k=0;
                int k1=0;
                while(k<onePath.length) {
                    float now = 0;
                    try {
                        now = Float.parseFloat(onePath[k]);
                    } catch (NumberFormatException e) {
                        k++;
                        continue;
                    }
                    //println(now);
                    if(now==now) {
                        CoOrds[k1] = now;
                        k1++;
                    }
                    //println(onePath[k] + "\t\t\t" + float(onePath[k]) + "\t" + float(onePath[k+1]));
                    k++;
                }
                for(int l=0;l<CoOrds.length;l+=2) {
                    System.out.println(CoOrds[l] + ",\t" + CoOrds[l+1]);
                }
            }
        }
        return CoOrds;
    }
}
