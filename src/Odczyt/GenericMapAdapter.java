
package Odczyt;

import org.w3c.dom.*;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

public class GenericMapAdapter extends XmlAdapter<Object,Map<?,?>> {

    @Override
    public Map<?, ?> unmarshal(Object domTree) {

        Map<Object, Object>    map = new HashMap<Object, Object>();
        Element content = (Element) domTree;
        NodeList childNodes = content.getChildNodes();
        if (childNodes.getLength() > 0) {
            for (int i = 0; i < childNodes.getLength(); i++)
            {
                Node child =childNodes.item(i);
                String key = child.getNodeName();
                // Skip text nodes
                if (key.startsWith("#"))continue;
                String value=((Text)child.getChildNodes().item(0))
                        .getWholeText();
                if(value.contentEquals("null")){
                    value=null;
                }
                map.put(key, value);
            }
        }
        return map;
    }


    @Override
    public Object marshal(Map<?, ?> map) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element customXml = doc.createElement("Map");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if(entry.getKey()!=null){
                    Element keyValuePair = doc.createElement(entry.getKey().toString());
                    if(entry.getValue()==null){
                        keyValuePair.appendChild(doc.createTextNode("null"));
                    }else{
                        keyValuePair.appendChild(doc.createTextNode(entry.getValue().toString()));
                    }
                    customXml.appendChild(keyValuePair);
                }
            }
            return customXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
