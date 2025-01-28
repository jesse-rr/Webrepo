public class Player {

    private String name;
    private double balance;
    private double winRate;

    public Player(String name, double balance, double winRate) {
        this.name = name;
        this.balance = balance;
        this.winRate = winRate;
    }
    public String getName() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public double getWinRate() {
        return winRate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
}
