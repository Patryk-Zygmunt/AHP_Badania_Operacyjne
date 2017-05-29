package Odczyt;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Linus on 09.03.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kryterium {

    private double wagaKryterium;
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    private String Porownywane;
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    private String Kategoria;
    @XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
    private String Nazwa;

    public Kryterium(double wagaKryterium, String porownywane, String kategoria, String nazwa) {
        this.wagaKryterium = wagaKryterium;
        Porownywane = porownywane;
        Kategoria = kategoria;
        Nazwa = nazwa;
    }

    public Kryterium() {
    }

    public double getWagaKryterium() {
        return wagaKryterium;
    }

    public String getPorownywane() {
        return Porownywane;
    }

    public String getKategoria() {
        return Kategoria;
    }

    public String getNazwa() {
        return Nazwa;
    }


    @Override
    public String toString() {
        return "Oblczenia.KryteriumObliczenia{" +
                "wagaKryterium=" + wagaKryterium +
                ", Porownywane='" + Porownywane + '\'' +
                ", Kategoria='" + Kategoria + '\'' +
                ", Nazwa='" + Nazwa + '\'' +
                '}';
    }
}
