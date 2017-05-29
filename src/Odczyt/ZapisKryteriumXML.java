package Odczyt;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Linus on 20.03.2017.
 */
public class ZapisKryteriumXML {

    public static void main(String[] args) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Kryterium.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Kryterium kryterium = new Kryterium(1, "Berlin/Paryz", "Mlekowita","Nazwa");
        marshaller.marshal(kryterium, new File("product.xml"));
    }
}
