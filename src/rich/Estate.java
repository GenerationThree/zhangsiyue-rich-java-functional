package rich;

public class Estate implements Land {
    private Player owner;
    private double price;

    public Estate(Player owner, double price) {
        this.owner = owner;
        this.price = price;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void buy(Player player) {
        owner = player;
    }

    @Override
    public double getPrice() {
        return price;
    }

}
