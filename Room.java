import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private List<Item> items;

    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public String getDescription() {
        return description;
    }

    public void addItem(Item item) {
        items.add(item);

    }

    public String getLongDescription() {
        String longDescription = "You are " + description + ".\n" + getExitString() + "\n";
        if (!items.isEmpty()) {
            longDescription += "Items in the room:\n";
            for (Item item : items) {
                longDescription += "- " + item.getDescriptionOfItem() + item.getWeightOfItem() + item.getWeightUnit() + "\n";
            }
        } else {
            longDescription += "There are no items in this room.\n";
        }
        return longDescription;
    }
}


