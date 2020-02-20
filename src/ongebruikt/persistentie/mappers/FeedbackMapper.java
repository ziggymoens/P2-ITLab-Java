package ongebruikt.persistentie.mappers;

import domein.Feedback;
import ongebruikt.persistentie.PersistentieController;

import java.util.ArrayList;
import java.util.List;

public abstract class FeedbackMapper {
    protected List<Feedback> feedbackList;
    protected PersistentieController persistentieController;

    public FeedbackMapper() {
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
