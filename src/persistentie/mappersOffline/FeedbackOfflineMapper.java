package persistentie.mappersOffline;

import domein.Feedback;
import domein.Gebruiker;
import domein.Sessie;
import exceptions.persistentie.FeedbackPersistentieException;
import persistentie.PersistentieController;
import persistentie.mappers.FeedbackMapper;
import java.io.*;
import java.util.List;

public class FeedbackOfflineMapper extends FeedbackMapper {

    private final File feedbackOffline = new File("src/offlineData/initData/Feedback");

    public FeedbackOfflineMapper() {
        super();
    }

    @Override
    public void initData() {
        if (Initialiseren.isInitialiseren()) {
            maakFeedback();
        } else {
            leesFeedback();
        }
    }

    private void maakFeedback() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(feedbackOffline));
            String line;
            while ((line = br.readLine()) != null) {
                String[] feedback = line.split(";");
                Gebruiker gebruiker = persistentieController.geefGebruikerMetCode(feedback[1]);
                Sessie sessie = persistentieController.geefSessieMetId(feedback[2]);
                feedbackList.add(new Feedback(feedback[0], sessie, gebruiker, feedback[3]));
            }
        } catch (IOException e) {
            throw new FeedbackPersistentieException("FeedbackOfflineMapper");
        }
        schrijfFeedback();
    }

    private void leesFeedback() {
        try {
            FileInputStream fis = new FileInputStream("src/offlineData/serieel/Feedback.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                Feedback feedback = (Feedback) ois.readObject();
                feedbackList.add(feedback);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new FeedbackPersistentieException("FeedbackOfflineMapper");
        }
    }

    @Override
    public void schrijfFeedback() {
        try {
            FileOutputStream fos = new FileOutputStream("src/offlineData/serieel/Feedback.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Feedback feedback : feedbackList) {
                oos.writeObject(feedback);
            }
        } catch (IOException e) {
            throw new FeedbackPersistentieException("FeedbackOfflineMapper");
        }
    }

    @Override
    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentieController = persistentieController;
    }

    @Override
    public List<Feedback> getFeedback() {
        return feedbackList;
    }

    @Override
    public void voegFeedbackToe(Feedback feedback) {
        feedbackList.add(feedback);
    }

    @Override
    public void verwijderFeedback(Feedback feedback) {
        feedbackList.remove(feedback);
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        throw new UnsupportedOperationException();
    }
}
