package  Odczyt;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * Created by Linus on 09.03.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Alternatywa {

    private String Nazwa;

    @XmlElement(name="mymap")

    @XmlJavaTypeAdapter(GenericMapAdapter.class)
    public Map<String,String> Wagi;


    public Alternatywa(String nazwa, Map<String, String> wektorWag) {
        Nazwa = nazwa;
        Wagi = wektorWag;
    }
    public String getNazwa() {
        return Nazwa;
    }


    public Map<String, String> getWagi() {
        return Wagi;
    }

    public Alternatywa() {
    }

    @Override
    public String toString() {
        return "Oblczenia.AlternatywaObliczenia{" +
                "Nazwa='" + Nazwa + '\'' +
                ", Wagi=" + Wagi +
                '}';
    }


}
