package Odczyt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Linus on 22.03.2017.
 */@XmlRootElement
public class AlternatywaList {
    private ArrayList<Alternatywa> lst=new ArrayList<Alternatywa>();

    @XmlElement(type= Alternatywa.class)
    public ArrayList<Alternatywa> getLst() {
        return lst;
    }

    public void setLst(ArrayList<Alternatywa> lst) {
        this.lst = lst;
    }
}
