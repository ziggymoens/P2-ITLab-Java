package persistentie.mappersOnline;

import domein.Aankondiging;
import domein.Feedback;
import persistentie.PersistentieController;
import persistentie.mappersAbs.AankondigingMapperAb;
import persistentie.mappersAbs.FeedbackMapperAb;

import java.util.List;

public class FeedbackOnlineMapper extends FeedbackMapperAb {

    public FeedbackOnlineMapper() {
        super();
    }

    @Override
    public void initData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Feedback> getFeedback() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void voegFeedbackToe(Feedback feedback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verwijderFeedback(Feedback feedback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void schrijfFeedback() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        throw new UnsupportedOperationException();
    }
}
