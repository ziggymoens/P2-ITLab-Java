package domein;

import java.io.Serializable;
import java.util.Objects;

public class Media implements Serializable {
    //region Variabelen
    //Primairy key
    private String mediaId;
    //endregion

    //region Constructor
    public Media() {
    }
    //endregion

    //region Setters
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    //endregion

    //region Getters

    //endregion

    //region Equals & hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(mediaId, media.mediaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
    //endregion
}
