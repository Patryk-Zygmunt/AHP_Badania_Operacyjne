package Oblczenia;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Linus on 10.03.2017.
 */
public class Dane {
private String pathAlt;
private String pathKryteria;

    public Document parserXML(String sciezka) throws IOException, org.xml.sax.SAXException, ParserConfigurationException {

        try{
            File plik = new File(sciezka);
            DocumentBuilderFactory dfF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbB = dfF.newDocumentBuilder();
              Document doc = dbB.parse(plik);
            return doc;
            }
            catch(Exception e){
            e.printStackTrace();
                return null;
            }
    }
        public ArrayList<KryteriumObliczenia> pobierzKryteria() {

            try {
                ArrayList<KryteriumObliczenia> kryteria=new ArrayList<KryteriumObliczenia>();
                Document doc= null;
                double waga=0;
                String Porownywane;
                String NazwaKryterium;
                String Kategoria;
                doc = parserXML(pathKryteria);

                NodeList KrytLista= doc.getElementsByTagName("kryteriumObliczenia");
              //  System.out.println(">>"+KrytLista);
                for(int i=0;i<KrytLista.getLength();i++){
                NodeList  childLista = KrytLista.item(i).getChildNodes();
                  //  System.out.println(">>>"+childLista.item(i).getNodeName());

                        NazwaKryterium=childLista.item(7).getTextContent();
                    waga=Double.parseDouble(childLista.item(1).getTextContent());
                        Kategoria=childLista.item(5).getTextContent();
                        Porownywane=childLista.item(3).getTextContent();
                      //  System.out.println(waga+" .>"+Porownywane+". >"+Kategoria);
                    kryteria.add(new KryteriumObliczenia(waga,Porownywane.toUpperCase(),Kategoria,NazwaKryterium));
                   i++; }
                System.out.println(kryteria);
                    return kryteria;
                }

             catch (Exception e) {
                e.printStackTrace();
            }
return null;

        }

    public Dane(String pathAlt, String pathKryteria) {
        this.pathAlt = pathAlt;
        this.pathKryteria = pathKryteria;
    }

    public  ArrayList<AlternatywaObliczenia> pobierzAlternatywy()  {
        Map<String,Map<String,Double>> alternatywy=new LinkedHashMap<>();
        ArrayList<Map<String,Double>> pomWag=new ArrayList<>();
        ArrayList<String> pomNazw=new ArrayList<>();
        ArrayList<AlternatywaObliczenia> Alternatywy=new ArrayList<>();
                  String nazwa;
                  Map<String,Double> wagi=new LinkedHashMap<>();
            try {
                Document doc=parserXML(pathAlt);
              //  Element ALT=doc.getDocumentElement();
              //  System.out.println("<"+ALT.getTagName()+">");

                NodeList AltLista=doc.getElementsByTagName("lst");
                for (int j = 0; j < AltLista.getLength(); j++) {
                    wagi=new LinkedHashMap<>();


                    NodeList OpcjaLista = AltLista.item(j).getChildNodes();
                    nazwa = OpcjaLista.item(1).getTextContent();
                    NodeList wagLista=OpcjaLista.item(3).getChildNodes();
                //   System.out.println("<"+nazwa+">");


                    for (int i = 1; i < wagLista.getLength();i++) {
                      //  System.out.println("<"+wagLista.item(i).getNodeName()+">");
                        //String value = wagLista.item(i).getNodeValue().trim();
                       // if (value.equals("")) continue;
                        //else{
                        alternatywy.put(nazwa,wagi);
                     wagi.put(wagLista.item(i).getNodeName(),Double.valueOf(wagLista.item(i).getTextContent()));

                        //System.out.println(wagLista.item(i).getNodeName()+":"+wagLista.item(i).getTextContent());
                  i++;  }
                    Alternatywy.add(new AlternatywaObliczenia(nazwa,wagi));

                }
                System.out.println(Alternatywy);
                return Alternatywy;
            }  catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        /*    public ArrayList<Oblczenia.AlternatywaObliczenia> budujAlternatywy(){
        ArrayList<Oblczenia.AlternatywaObliczenia> alternatywy=new ArrayList<>();
                Map<String,Map<String,Double>>
                        altMapa=pobierzAlternatywy();
               // System.out.println(altMapa);
                for (Map.Entry<String,Map<String,Double>> entry : altMapa.entrySet()) {
                    alternatywy.add(new Oblczenia.AlternatywaObliczenia(entry.getKey(),entry.getValue()));
                }
                return  alternatywy;}*/

    public static void main(String[] args) throws
            ParserConfigurationException, IOException, org.xml.sax.SAXException {
        /*File plik = new File("Kotek.xml");
        DocumentBuilderFactory dfF = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbB = dfF.newDocumentBuilder();
        Document doc = dbB.parse(plik);
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        Element kot=doc.getElementById("k");
        NodeList nodeList = kot.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            // lista “dzieci” i-tego itema
            Node n = nodeList.item(i);
             System.out.println(n.getTextContent());
        }*/

       //Dane dane=new Dane();
       //System.out.println(dane.pobierzAlternatywy());
      // System.out.println(dane.pobierzKryteria());
    }
}