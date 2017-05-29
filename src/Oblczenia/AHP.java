package Oblczenia;

import Jama.Matrix;

import java.util.*;

//jama

/**
 * Created by Linus on 06.03.2017.
 */
public class AHP {
    private ArrayList<AlternatywaObliczenia> Alt = new ArrayList<>();
    private int iloscAlternatyw;
    private ArrayList<Matrix> Mac = new ArrayList<>();
    private ArrayList<KryteriumObliczenia> Kryteria = new ArrayList<>();
    private ArrayList<String> Kategorie = new ArrayList<>();
    private ArrayList<String> KryteriaUnikalneNazwy;
    private Map<String, double[]> WektorAlternatwyDlaKryterium = new LinkedHashMap<>();
    private Map<String, ArrayList<KryteriumObliczenia>> mapaKryteria = new LinkedHashMap<>();
    private Map<String, LinkedHashSet<String>> mapaZbiorKryteria = new LinkedHashMap<>();
    private Map<String, String> mapaZbiorKategoriaIKryteria = new LinkedHashMap<>();
    private Map<String, double[]> WYnikKryteriumWektor = new LinkedHashMap<>();
    private Map<String, Double> NazwaLokalnaWartoscf = new LinkedHashMap<>();
    private Map<String, Double> NAzwaWartoscGlobalnaWagiKryterium = new LinkedHashMap<>();
    private Map<String, ArrayList<String>> mapaZbiorNazwKryteria = new LinkedHashMap<>();
    private Map<String, Double> KryteriaDoWYliczeń = new LinkedHashMap<>();
    private ArrayList<Double> wynik = new ArrayList<>();


    public AHP(Dane dane) {

        // System.out.println(dane.pobierzKryteria());
        Alt.addAll(dane.pobierzAlternatywy());
        Kryteria.addAll(dane.pobierzKryteria());
        //  System.out.println(Alt);
        iloscAlternatyw = (int) Math.sqrt((double) (Alt.size()));
        mapaKryteria = grupujKryteria();
        for (Map.Entry<String, LinkedHashSet<String>> entry : mapaZbiorKryteria.entrySet()) {
            mapaZbiorNazwKryteria.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        NAzwaWartoscGlobalnaWagiKryterium.put("BRAK", 1.0);
        wyliczLokalneWartosciWagiKryteriow();
    }


    //tworzy macierz porównań ALternatyw dla wybranego Oblczenia.KryteriumObliczenia
    public Matrix tworzMacierzPorownanDlaAlternatywy(String kryterium) {
        double[][] tab = new double[iloscAlternatyw][iloscAlternatyw];
        int n = 0;
        for (int i = 0; i < iloscAlternatyw; i++) {
            for (int j = 0; j < iloscAlternatyw; j++) {
                tab[i][j] = Alt.get(n).getWektorWag().get(kryterium);
                n++;
            }
        }
        Matrix matrix = new Matrix(tab);


        return matrix;
    }

    //zwraca mapa<Kategoria,Wektor>
    public Map<String, double[]> tworzWektorKryteriowDlaWszystkichKategori() {
        Map<String, double[]> matrixMap = new LinkedHashMap<>();
        for (String kategorie : Kategorie
                ) {
            matrixMap.put(kategorie, liczWektorWlasny(makeKryteriumMatrix(kategorie)));
        }
        return matrixMap;
    }

    /**
     * @return mapa<Kategoria,WektorPriorytetow>
     */
    public Map<String, Matrix> tworzMacierzPorownanDlaWszystkichKategori() {
        Map<String, Matrix> matrixMap = new LinkedHashMap<>();
        for (String kategorie : Kategorie
                ) {
            matrixMap.put(kategorie, makeKryteriumMatrix(kategorie));
        }
        return matrixMap;
    }

    //zwraca mape<Oblczenia.KryteriumObliczenia,Lokalna Wartosc Wagi kryterium>
    public Map<String, Double> wyliczLokalneWartosciWagiKryteriow() {
        Map<String, Double> WagMap = new HashMap<>();
        Map<String, double[]> VectorMap = tworzWektorKryteriowDlaWszystkichKategori();
        for (int i = 0; i < Kategorie.size(); i++) {
            for (int j = 0; j < mapaZbiorNazwKryteria.get(Kategorie.get(i)).size(); j++) {
                WagMap.put((mapaZbiorNazwKryteria.get(Kategorie.get(i))).get(j), ((VectorMap.get((Kategorie.get(i))))[j]));
            }
        }
        NazwaLokalnaWartoscf = WagMap;
        return WagMap;
    }

    /**
     * @return <>Oblczenia.KryteriumObliczenia,Macierz</>
     */
    public Map<String, Matrix> tworzMacierzPorownanALternatywDlaWszystkichKryteriow() {
        Map<String, Matrix> mapaMApcierzy = new LinkedHashMap<>();
        for (String kryterium : KryteriaUnikalneNazwy
                ) {
            mapaMApcierzy.put(kryterium, tworzMacierzPorownanDlaAlternatywy(kryterium));
        }
        return mapaMApcierzy;
    }

    public void tworzWEktorAlternatywDlaWszystkichKryterium() {

        for (String kryterium : KryteriaUnikalneNazwy
                ) {
            WektorAlternatwyDlaKryterium.put(kryterium, liczWektorWlasny(tworzMacierzPorownanDlaAlternatywy(kryterium)));
        }
    }

    //Oblicz Wartości GLobalnych Wag Dla wszystkich Kryteriow
    public void LiczWEktorGlobalnyWwag() {
        for (Map.Entry<String, String> entry : mapaZbiorKategoriaIKryteria.entrySet()) {
            liczWartosćGlobalną(entry.getKey(), entry.getValue());
        }
    }

    // uzywaqne przez LiczWEktorGloblnychWag
    public void liczWartosćGlobalną(String Nazwa, String kategoria) {
        if (NAzwaWartoscGlobalnaWagiKryterium.containsKey(kategoria)) {
            NAzwaWartoscGlobalnaWagiKryterium.put(Nazwa, NAzwaWartoscGlobalnaWagiKryterium.get(kategoria) * NazwaLokalnaWartoscf.get(Nazwa));
        } else {
            liczWartosćGlobalną(kategoria, mapaZbiorKategoriaIKryteria.get(kategoria));
        }


    }
    public double countErrorFactor(Matrix matrix){
        double[] errorFactors = matrix.eig().getRealEigenvalues();
        double errorFactor = -10.0;

        for(double d : errorFactors){
            if(d>errorFactor){
                errorFactor = d;
            }
        }

        return errorFactor;
    }


    public Matrix makeKryteriumMatrix(String kategoria) {
        int iloscKryteriow = (int) Math.sqrt(mapaKryteria.get(kategoria).size());
        double[][] tab = new double[iloscKryteriow][iloscKryteriow];
        int n = 0;
        for (int i = 0; i < iloscKryteriow; i++) {
            for (int j = 0; j < iloscKryteriow; j++) {
                tab[i][j] = mapaKryteria.get(kategoria).get(n).getWagaKryterium();
                n++;
            }
        }
        Matrix matrix = new Matrix(tab);
        // matrix.print(3,2);
        return matrix;
    }


    public ArrayList<Matrix> makeKryteriaMatixForAllKategorie() {
        ArrayList<Matrix> matrixList = new ArrayList<>();
        for (String kategorie : Kategorie
                ) {
            matrixList.add(makeKryteriumMatrix(kategorie));
        }
        return matrixList;


    }

    public double[] liczWektorWlasny(Matrix matrix) {

        double[] wektorWlasny = new double[matrix.getArray().length];
        double[] WEktorWlasnypom = new double[wektorWlasny.length];
        int n = 0;

        for (double[] row : matrix.getArray()) {
            double sum = 1;
            for (double element : row) {
                sum *= element;}
            double pom = Math.pow(sum, 1.0 / row.length);
            WEktorWlasnypom[n] = pom;
            n++;
        }

        wektorWlasny = normaqlizujWektor(WEktorWlasnypom);

        return wektorWlasny;
    }

    public double sumuj(double[] array) {
        double sum = 0;
        for (double d : array) {
            sum += d;
        }
        return sum;
    }

    public double[] normaqlizujWektor(double[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) array[i] = -array[i];
        }

        double sum = sumuj(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = (double) Math.round(Math.abs(array[i]) / sum * 1000d) / 1000d;
        }
        return array;
    }


    public ArrayList<Double> liczMacierzDlaKryterium(KryteriumObliczenia kryteriumObliczenia) {
        ArrayList<Double> macierzKryterium = new ArrayList<>();
        for (int i = 0; i < Alt.size(); i++) {
            for (int j = 0; j < Alt.size(); j++) {
                macierzKryterium.add((Alt.get(i).getWektorWag().get(kryteriumObliczenia)) / (Alt.get(j).getWektorWag().get(kryteriumObliczenia)));
            }
        }
        return macierzKryterium;
    }

    public Map<KryteriumObliczenia, ArrayList<Double>> liczMacierze() {
        Map<KryteriumObliczenia, ArrayList<Double>> wektorMacierzy = new LinkedHashMap<>();
        for (KryteriumObliczenia kryteriumObliczenia : Kryteria) {
            wektorMacierzy.put(kryteriumObliczenia, liczMacierzDlaKryterium(kryteriumObliczenia));
        }
        return wektorMacierzy;
    }

    public ArrayList<Double> liczWektorWynikowDlaKruterium(KryteriumObliczenia kryteriumObliczenia) {
        ArrayList<Double> WektorWynikKryterium = new ArrayList<>();
        double sumaWag = 0;
        // System.out.println(">>p>"+Alt);
        for (int i = 0; i < Alt.size(); i++) {
            //  System.out.println(">>p>"+Alt.get(i).getWektorWag().get(kryteriumObliczenia.getPorownywane()));
            sumaWag += (Alt.get(i).getWektorWag().get(kryteriumObliczenia.getPorownywane()));
        }

        for (int i = 0; i < Alt.size(); i++)
            WektorWynikKryterium.add((Alt.get(i).getWektorWag().get(kryteriumObliczenia.getPorownywane())) / sumaWag);

        return WektorWynikKryterium;
    }

    public Map<String, double[]> liczWektorWynikuDlaKryterium() {
        LiczWEktorGlobalnyWwag();
        tworzWEktorAlternatywDlaWszystkichKryterium();
        ArrayList<ArrayList<Double>> WektorWynik = new ArrayList<>();
        ArrayList<Double> Wynik = new ArrayList<>();

        //
        ArrayList<Double> WektorZWagKryterow = new ArrayList<>();
        for (Map.Entry<String, Double> entry : NAzwaWartoscGlobalnaWagiKryterium.entrySet()) {
            if ((Kategorie.contains(entry.getKey()))) {
                //
            } else {

                KryteriaDoWYliczeń.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, Double> entry : KryteriaDoWYliczeń.entrySet()) {
            double[] wektor = new double[(WektorAlternatwyDlaKryterium.get(entry.getKey())).length];
            for (int j = 0; j < (WektorAlternatwyDlaKryterium.get(entry.getKey())).length; j++) {
                wektor[j] = KryteriaDoWYliczeń.get(entry.getKey()) * WektorAlternatwyDlaKryterium.get(entry.getKey())[j];
                // System.out.println(wektor[j]);
            }

            WYnikKryteriumWektor.put(entry.getKey(), wektor);
        }

        return WYnikKryteriumWektor;
    }

    //zwraca koncowy wynik
    public ArrayList<Double> liczWektorWyniku() {
        liczWektorWynikuDlaKryterium();
        double suma;
        for (Map.Entry<String, double[]> entry : WYnikKryteriumWektor.entrySet()) {
            suma = 0;

            for (int j = 0; j < (WektorAlternatwyDlaKryterium.get(entry.getKey())).length; j++) {
                suma += (entry.getValue())[j];
            }
            wynik.add(suma);
        }
        return wynik;
    }


    public void wypiszWynik() {
        System.out.println("Kryteria>>");
        System.out.println(mapaZbiorKategoriaIKryteria);
        Map<String, Matrix> mapaMApcierzy = tworzMacierzPorownanALternatywDlaWszystkichKryteriow();
        Map<String, double[]> WEktorProirytetowDlaKryteriów = tworzWektorKryteriowDlaWszystkichKategori();
        Map<String, Matrix> mapaMApcierzyPOrównańKryteriów = tworzMacierzPorownanDlaWszystkichKategori();
        LinkedHashSet<String> NazwyAlternatywyZbior = new LinkedHashSet<>();

        for (int i = 0; i < Alt.size(); i++) {
            String[] parts = Alt.get(i).getNazwa().split("/");
            NazwyAlternatywyZbior.add(parts[0]);
        }
        ArrayList<String> NazwyAlternatywy = new ArrayList<>(NazwyAlternatywyZbior);
        System.out.println();
        System.out.println("Alternatywy = " + NazwyAlternatywy);
        System.out.println();
        for (Map.Entry<String, Matrix> entry : mapaMApcierzy.entrySet()) {
            System.out.println("Macierz  porównań alternatyw dla kryterium: " + entry.getKey());
            entry.getValue().print(4, 2);
        }
        for (Map.Entry<String, Matrix> entry : mapaMApcierzyPOrównańKryteriów.entrySet()) {
            System.out.println("Macierz  porównań Kryteriów dla nadrzednego Oblczenia.KryteriumObliczenia : " + entry.getKey());
            entry.getValue().print(4, 2);
        }

        liczWektorWyniku();
        System.out.println("Wektory priorytetów dla Kryteriów>>");
        for (Map.Entry<String, double[]> entry : WEktorProirytetowDlaKryteriów.entrySet()) {
            // for(int j=0;j<(WektorAlternatwyDlaKryterium.get(entry.getKey())).length;j++){
            System.out.println("Oblczenia.KryteriumObliczenia Nadrzędne>> " + entry.getKey() + " Wektor priorytetów: " + Arrays.toString(entry.getValue()));
            ;
        }
        System.out.println();
        System.out.println("Wektory priorytetów dla Alternatyw>>");
        for (Map.Entry<String, double[]> entry : WYnikKryteriumWektor.entrySet()) {
            // for(int j=0;j<(WektorAlternatwyDlaKryterium.get(entry.getKey())).length;j++){
            System.out.println("Oblczenia.KryteriumObliczenia>> " + entry.getKey() + " Wektor priorytetów: " + Arrays.toString(entry.getValue()));
            ;
        }
        System.out.println();
        for (int i = 0; i < wynik.size(); i++) {
            System.out.println("Oblczenia.AlternatywaObliczenia: " + NazwyAlternatywy.get(i) + " Wynik " + wynik.get(i));
        }


    }


    public Map<String, ArrayList<KryteriumObliczenia>> grupujKryteria() {
        ArrayList<KryteriumObliczenia> wynik = new ArrayList();
        int amount = 0;
        LinkedHashSet<String> KryteriaNazwy = new LinkedHashSet<>();
        Map<String, ArrayList<KryteriumObliczenia>> mapa = new LinkedHashMap<>();
        for (KryteriumObliczenia kryteriumObliczenia : Kryteria
                ) {

            if (mapa.containsKey(kryteriumObliczenia.getKategoria())) {
                mapa.get(kryteriumObliczenia.getKategoria()).add(kryteriumObliczenia);
                mapaZbiorKryteria.get(kryteriumObliczenia.getKategoria()).add(kryteriumObliczenia.getNazwa());
                mapaZbiorKategoriaIKryteria.put(kryteriumObliczenia.getNazwa(), kryteriumObliczenia.getKategoria());
                KryteriaNazwy.add(kryteriumObliczenia.getNazwa());

            } else {
                mapa.put(kryteriumObliczenia.getKategoria(), new ArrayList<KryteriumObliczenia>());
                mapaZbiorKryteria.put(kryteriumObliczenia.getKategoria(), new LinkedHashSet<String>());
                mapa.get(kryteriumObliczenia.getKategoria()).add(kryteriumObliczenia);
                mapaZbiorKryteria.get(kryteriumObliczenia.getKategoria()).add(kryteriumObliczenia.getNazwa());
                Kategorie.add(kryteriumObliczenia.getKategoria());
                mapaZbiorKategoriaIKryteria.put(kryteriumObliczenia.getNazwa(), kryteriumObliczenia.getKategoria());
                KryteriaNazwy.add(kryteriumObliczenia.getNazwa());

            }
        }

        KryteriaUnikalneNazwy = new ArrayList<String>(KryteriaNazwy);
        return mapa;
    }


    public static void main(String[] args) {
       // Oblczenia.AHP ahp = new Oblczenia.AHP();
        //  (ahp.makeKryteriumMatrix("KLIMAT")).print(3,2);
        // System.out.println(ahp.mapaZbiorKategoriaIKryteria);
        System.out.println(">>>>>>>>>>>");
        // for(int i=0;i<3;i++) {
        //(ahp.tworzMacierzPorownanALternatywDlaWszystkichKryteriow()).get(i).print(3, 2);
       // ahp.wypiszWynik();
    }       //Matrix mat=new Matrix(ahp.iloscAlternatyw,ahp.iloscAlternatyw);
    // mat.clone();
    // mat.print(0,3);






/*  double[][] ar={{1,2},{2,2}};
  Matrix A  = new Matrix(ar);
  EigenvalueDecomposition e= A.eig();
  double[] eig=e.getRealEigenvalues();
  for(int i =0;i<eig.length;i++)
  A.print(3,4);*/

}