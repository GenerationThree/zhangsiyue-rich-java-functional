package rich;

public class Estate implements Land {
    private Player owner;
    private double price;
    private Level level;

    public Estate(Player owner, double price) {
        this.owner = owner;
        this.price = price;
    }

    public static Estate createEstateWithLevel(Player owner, double price, Level level) {
        Estate estate = new Estate(owner, price);
        estate.level = level;
        return estate;
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

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void promote() {
        level = level.next();
    }

    public enum Level {
        ZERO {
            @Override
            public Level next() {
                return ONE;
            }
        },
        ONE {
            @Override
            public Level next() {
                return TWO;
            }
        },
        TWO {
            @Override
            public Level next() {
                return TOP;
            }
        },
        TOP {
            @Override
            public Level next() {
                return null;
            }
        };

        public abstract Level next();
    }

}
