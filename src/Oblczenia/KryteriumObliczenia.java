package Oblczenia;

/**
 * Created by Linus on 09.03.2017.
 */
public class KryteriumObliczenia {

    private double wagaKryterium;

    private String Porownywane;
    private String Kategoria;
    private String Nazwa;

    public KryteriumObliczenia(double wagaKryterium, String porownywane, String kategoria, String nazwa) {
        this.wagaKryterium = wagaKryterium;
        Porownywane = porownywane;
        Kategoria = kategoria;
        Nazwa = nazwa;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KryteriumObliczenia kryteriumObliczenia = (KryteriumObliczenia) o;

        if (Double.compare(kryteriumObliczenia.wagaKryterium, wagaKryterium) != 0) return false;
        if (Porownywane != null ? !Porownywane.equals(kryteriumObliczenia.Porownywane) : kryteriumObliczenia.Porownywane != null) return false;
        if (Kategoria != null ? !Kategoria.equals(kryteriumObliczenia.Kategoria) : kryteriumObliczenia.Kategoria != null) return false;
        return Nazwa != null ? Nazwa.equals(kryteriumObliczenia.Nazwa) : kryteriumObliczenia.Nazwa == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(wagaKryterium);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (Porownywane != null ? Porownywane.hashCode() : 0);
        result = 31 * result + (Kategoria != null ? Kategoria.hashCode() : 0);
        result = 31 * result + (Nazwa != null ? Nazwa.hashCode() : 0);
        return result;
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
