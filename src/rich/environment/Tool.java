package rich.environment;


public class Tool {
    private Type type;
    private int pointPrice;

    public Tool(Type type) {
        this.type = type;
        pointPrice = this.type.getPointPrice();
    }

    public int getPointPrice() {
        return pointPrice;
    }

    public Type getType() {
        return type;
    }

    public enum  Type {
        BLOCK {
            @Override
            public int getPointPrice() {
                return 50;
            }
        },
        ROBOT {
            @Override
            public int getPointPrice() {
                return 30;
            }
        },
        BOMB {
            @Override
            public int getPointPrice() {
                return 50;
            }
        };

        public abstract int getPointPrice();
    }
}
