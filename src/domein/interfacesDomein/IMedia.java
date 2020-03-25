package domein.interfacesDomein;

import java.awt.image.BufferedImage;

public interface IMedia {
    BufferedImage getAfbeeding() /*throws IOException*/;

    String getMediaId();

    String getUrl();

    String getTypeString();

    IGebruiker getIGebruiker();

    String toString_Compleet();
}
