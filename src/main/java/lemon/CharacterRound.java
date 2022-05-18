package lemon;

import pt.up.hs.uhc.models.Stroke;

import java.util.List;

public class CharacterRound {
    private final int characterNumber;
    private final String roundStartTimestamp;

    private List<Stroke> strokes;

    public CharacterRound(int characterNumber, String roundStartTimestamp){
        this.characterNumber = characterNumber;
        this.roundStartTimestamp = roundStartTimestamp;
    }

    public void addCharacterStrokes(List<Stroke> strokes){
        this.strokes.addAll(strokes);
    }
}
