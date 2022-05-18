package lemon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingSession {
    private final List<CharacterRound> characterRounds;
    private final String timestamp;

    private Date startTime;

    public TrainingSession(int numberOfRounds, String timestamp){
        characterRounds = new ArrayList<>(numberOfRounds);
        this.timestamp = timestamp;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        try {
            startTime = sdf.parse(timestamp);
            System.out.println(startTime.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public CharacterRound createCharacterRound(int characterIndex, String characterRoundStartTimestamp){
        CharacterRound characterRound = new CharacterRound(characterIndex, characterRoundStartTimestamp);
        characterRounds.add(characterRound);
        return characterRound;
    }
}
