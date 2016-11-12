package rich.environment;

import rich.Player;

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
    public boolean promote() {
        if (level.compareTo(Level.TOP) < 0) {
            level = level.next();
            return true;
        }
        return false;

    }

    public enum Level {
        ZERO {
            @Override
            public Level next() {
                return ONE;
            }

            @Override
            public double getTimes() {
                return 0.5;
            }
        },
        ONE {
            @Override
            public Level next() {
                return TWO;
            }

            @Override
            public double getTimes() {
                return 1;
            }
        },
        TWO {
            @Override
            public Level next() {
                return TOP;
            }

            @Override
            public double getTimes() {
                return 2;
            }
        },
        TOP {
            @Override
            public Level next() {
                return null;
            }

            @Override
            public double getTimes() {
                return 4;
            }
        };

        public abstract Level next();

        public abstract double getTimes();
    }

}
