public enum PlayerDecision {
    Hit("Hit"),
    Stand("Stand"),
    Double_down("Double down"),
    Split("Split"),
    Surrender("Surrender");

    private final String decision;

    PlayerDecision(String decision) {
        this.decision = decision;
    }

    public String getDecision() {
        return decision;
    }
}
