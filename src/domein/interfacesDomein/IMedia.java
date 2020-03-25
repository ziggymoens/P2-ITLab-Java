package domein.interfacesDomein;

import java.awt.image.BufferedImage;

public interface IMedia {
    BufferedImage getAfbeeding() /*throws IOException*/;

    String getMediaId();

    String getLocatie();

    String getTypeString();

    IGebruiker getIGebruiker();

    String toString_Compleet();
}
