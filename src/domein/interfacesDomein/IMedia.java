package domein.interfacesDomein;

public interface IMedia {
    int getMediaId();

    String getLocatie();

    String getTypeString();

    IGebruiker getIGebruiker();
}
