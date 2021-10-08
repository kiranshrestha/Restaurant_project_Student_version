public class Item {
    private final String name;
    private final int price;
    private boolean selected;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return  name + ":"
                + price + " " + isSelected()
                + "\n"
                ;
    }
}
