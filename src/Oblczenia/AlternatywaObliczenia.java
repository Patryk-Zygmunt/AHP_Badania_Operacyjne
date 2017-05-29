package Oblczenia;

import java.util.Map;

/**
 * Created by Linus on 09.03.2017.
 */
public class AlternatywaObliczenia {
    public AlternatywaObliczenia(String nazwa, Map<String, Double> wektorWag) {
        Nazwa = nazwa;
        WektorWag = wektorWag;
    }

    private String Nazwa;

    public String getNazwa() {
        return Nazwa;
    }

    @Override
    public String toString() {
        return "Oblczenia.AlternatywaObliczenia{" +
                "Nazwa='" + Nazwa + '\'' +
                ", WektorWag=" + WektorWag +
                '}';
    }

    public Map<String, Double> getWektorWag() {
        return WektorWag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlternatywaObliczenia that = (AlternatywaObliczenia) o;

        if (Nazwa != null ? !Nazwa.equals(that.Nazwa) : that.Nazwa != null) return false;
        return WektorWag != null ? WektorWag.equals(that.WektorWag) : that.WektorWag == null;
    }

    @Override
    public int hashCode() {
        int result = Nazwa != null ? Nazwa.hashCode() : 0;
        result = 31 * result + (WektorWag != null ? WektorWag.hashCode() : 0);
        return result;
    }

    private Map<String,Double> WektorWag;

}
