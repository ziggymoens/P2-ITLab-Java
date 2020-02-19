package persistentie.mappers;

import domein.Feedback;
import persistentie.Connection;
import persistentie.PersistentieController;
import persistentie.mappersAbs.FeedbackMapperAb;
import persistentie.mappersOffline.FeedbackOfflineMapper;
import persistentie.mappersOnline.FeedbackOnlineMapper;

import java.util.List;

public class FeedbackMapperController {
    private FeedbackMapperAb mapper;

    public FeedbackMapperController() {
        if(Connection.isONLINE()){
            mapper = new FeedbackOnlineMapper();
        } else{
            mapper = new FeedbackOfflineMapper();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController){
        mapper.setPersistentieController(persistentieController);
    }

    public List<Feedback> getFeedback() {
        return mapper.getFeedback();
    }

    public void voegAankondigingToe(Feedback feedback) {
        mapper.voegFeedbackToe(feedback);
    }

    public void updateAankondiging(Feedback feedback) {
        throw new UnsupportedOperationException();
    }

    public void verwijderAankondiging(Feedback feedback) {
        mapper.verwijderFeedback(feedback);
    }

    public void schrijfAankondigingen() {
        mapper.schrijfFeedback();
    }

    public void initData() {
        mapper.initData();
    }
}
