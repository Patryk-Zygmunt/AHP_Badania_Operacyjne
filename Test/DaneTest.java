import Oblczenia.Dane;
import org.junit.jupiter.api.Test;

/**
 * Created by Linus on 11.03.2017.
 */
class DaneTest {
    @Test
    void parserXML() {

    }

    @Test
    void pobierzAlternatywy() {
        Dane dane = new Dane("alt.xml","kryt.xml");
        System.out.println(dane.pobierzAlternatywy());
    }

    @Test
    void pobierzKryteria() {
        Dane dane = new Dane("alt.xml","kryt.xml");
        System.out.println(dane.pobierzKryteria());

    }

    @Test
    void main() {

    }

}