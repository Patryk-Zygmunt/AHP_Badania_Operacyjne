package Odczyt;

import javax.xml.bind.JAXBContext;


import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 20.03.2017.
 */
public class ZapisAlternatywaXML {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        File file=new File("alt.xml");
        JAXBContext ctx = JAXBContext.newInstance(AlternatywaList.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Map<String,String> map=new HashMap<String, String>();
        map.put("Ka","1.0");
        map.put("Kb","2.2");
        map.put("Kc","0.2");
        Map<String,String> map2=new HashMap<String, String>();
        map2.put("Ka","0.5");
        map2.put("Kb","1.0");
        map2.put("Kc","1.2");
        Alternatywa alt =new Alternatywa("Berlin/Berlin",map);
        Alternatywa alt2 =new Alternatywa("Berlin/Paryz",map);
        Map<String,String> map3=new HashMap<String, String>();
        map.put("Ka","1.0");
        map.put("Kb","2.2");
        map.put("Kc","0.2");
        Map<String,String> map4=new HashMap<String, String>();
        map2.put("Ka","1.5");
        map2.put("Kb","1.0");
        map2.put("Kc","1.2");
        Alternatywa alt3 =new Alternatywa("Paryz/Berlin",map);
        Alternatywa alt4 =new Alternatywa("Paryz/Paryz",map);

        AlternatywaList lst=new AlternatywaList();
        lst.getLst().add(alt);
        lst.getLst().add(alt2);
        lst.getLst().add(alt3);
        lst.getLst().add(alt4);




        marshaller.marshal(lst,file);



    }


}
