package persistentie.repositories;

import domein.Feedback;
import exceptions.persistentie.FeedbackPersistentieException;
import persistentie.PersistentieController;
import persistentie.mappercontrollers.FeedbackMapperController;

import java.util.List;

public class FeedbackRepository {
    private FeedbackMapperController mapper;

    public FeedbackRepository() {
        this.mapper = new FeedbackMapperController();
    }

    public void setPersistenieController(PersistentieController pc){
        mapper.setPersistentieController(pc);
    }

    public List<Feedback> getFeedback() {
        return mapper.getFeedback();
    }

    public void beheerFeedback(String optie, Feedback feedback) {
        switch (optie) {
            case "CREATE":
                mapper.voegAankondigingToe(feedback);
                break;
            case "READ":
                geefFeedbackgMetId(feedback.getFeedbackId());
                break;
            case "UPDATE":
                mapper.updateAankondiging(feedback);
                break;
            case "DELETE":
                mapper.verwijderAankondiging(feedback);
                break;
            default:
                throw new FeedbackPersistentieException("FeedbackRepository");
        }
    }

    private Feedback geefFeedbackgMetId(String id) {
        return mapper.getFeedback().stream().filter(a -> a.getFeedbackId().equals(id)).findFirst().orElse(null);
    }

    public void schrijfWeg() {
        mapper.schrijfAankondigingen();
    }

    private void update() {
        throw new UnsupportedOperationException();
    }

    public void initData() {
        mapper.initData();
    }
}
