package Odczyt;

import Oblczenia.AHP;
import Oblczenia.Dane;
import org.junit.jupiter.api.Test;




/**
 * Created by Linus on 22.03.2017.
 */
class SprawdzSpojnoscTest {
    @Test
    void countErrorFactor() {
        Dane dane = new Dane("alt.xml","kryt.xml");
        AHP ahp=new AHP(dane);

        //ahp.wypiszWynik();
        SprawdzSpojnosc spr=new SprawdzSpojnosc(ahp.tworzMacierzPorownanDlaAlternatywy("Ka"));
        System.out.println(spr.countErrorFactor());


    }

}