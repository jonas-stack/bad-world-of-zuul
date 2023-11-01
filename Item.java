public class Item {

    private String descriptionOfItem;
    private int weightOfItem;
    private String weightUnit;

    // Constructor for the Item class
    public Item(String descriptionOfItem, int weightOfItem, String weightUnit) {
        this.descriptionOfItem = descriptionOfItem;
        this.weightOfItem = weightOfItem;
        this.weightUnit = getWeightUnit();
    }

    // Getter method for retrieving the description of the item
    public String getDescriptionOfItem() {
        return descriptionOfItem;
    }

    // Getter method for retrieving the weight of the item
    public int getWeightOfItem() {
        return weightOfItem;
    }

    public String getWeightUnit() {
        return weightUnit;
    }
}

