package ongebruikt.persistentie.mappercontrollers;

import domein.Feedback;
import ongebruikt.persistentie.Connection;
import ongebruikt.persistentie.PersistentieController;
import ongebruikt.persistentie.mappers.FeedbackMapper;
import ongebruikt.persistentie.mappers.mappersOffline.FeedbackOfflineMapper;
import ongebruikt.persistentie.mappers.mappersOnline.FeedbackOnlineMapper;

import java.util.List;

public class FeedbackMapperController {
    private FeedbackMapper mapper;

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
