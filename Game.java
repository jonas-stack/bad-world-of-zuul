/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, office, foyer, diningHall, kitchen, ingleNook, storeroom, bathroom, secretRoom, secondFloor, basement;
      
        // create the rooms
        outside = new Room("outside the main entrance of the HarringtonHall. the Haunted Mansion");
        foyer = new Room("in the foyer with the entrance to the manor");
        diningHall = new Room("in the dinningHall with the big dinning table");
        kitchen = new Room("in the kitchen with stove and basement stairs");
        ingleNook = new Room("in the inglenook with a fireplace");
        office = new Room("in the office with two chairs and a table");
        storeroom = new Room("in the storeroom with the big metal shelf");
        bathroom = new Room ("in the bathroom with the bathtub");
        secretRoom = new Room (" in the secret room with 3 big lights");
        secondFloor = new Room (" in the second floor of the mannor");
        basement = new Room(" in the basement of the mannor");
        
        // initialise room exits
        outside.setExit("north", foyer);

        foyer.setExit("north", ingleNook);
        foyer.setExit("east", bathroom);
        foyer.setExit("west", storeroom);

        bathroom.setExit("west", foyer);

        storeroom.setExit("east", foyer);

        ingleNook.setExit("south", foyer);
        ingleNook.setExit("east", diningHall);
        ingleNook.setExit("west", office);
        ingleNook.setExit("up", secondFloor);

        secondFloor.setExit("down", ingleNook);

        diningHall.setExit("south", kitchen);
        diningHall.setExit("west", ingleNook);

        kitchen.setExit("north", diningHall);
        kitchen.setExit("down", basement);

        basement.setExit("up", kitchen);

        office.setExit("east", ingleNook);
        office.setExit("south", secretRoom);

        secretRoom.setExit("north", office);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());

        if(currentRoom.getExit("north") != null) {
            System.out.print("north ");
        }
        if(currentRoom.getExit("east") != null) {
            System.out.print("east ");
        }
        if(currentRoom.getExit("south") != null) {
            System.out.print("south ");
        }
        if(currentRoom.getExit("west") != null) {
            System.out.print("west ");
        }
        if(currentRoom.getExit("up") != null) {
            System.out.print("up ");
        }
        if(currentRoom.getExit("down") != null) {
            System.out.print("down ");
        }
        System.out.println();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExit("south");
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExit("west");
        }
        if(direction.equals("up")) {
            nextRoom = currentRoom.getExit("up");
        }
        if(direction.equals("down")) {
            nextRoom = currentRoom.getExit("down");
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
