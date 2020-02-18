package persistentie.mappersAbs;

import domein.Aankondiging;
import domein.Feedback;
import persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class FeedbackMapperAb {
    protected List<Feedback> feedbackList;
    protected PersistentieController persistentieController;

    public FeedbackMapperAb() {
        this.feedbackList = new ArrayList<>();
    }

    public abstract List<Feedback> getFeedback();

    public abstract void voegFeedbackToe(Feedback feedback);

    public abstract void verwijderFeedback(Feedback feedback);

    public abstract void updateFeedback(Feedback feedback);

    public abstract void schrijfFeedback();

    public abstract void setPersistentieController(PersistentieController persistentieController);

    public abstract void initData();
}
