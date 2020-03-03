package domeintje.interfacesDomein;

public interface IMedia {
    String getMediaId();

    String getLocatie();

    String getTypeString();

    IGebruiker getIGebruiker();

    String toString_Compleet();
}
