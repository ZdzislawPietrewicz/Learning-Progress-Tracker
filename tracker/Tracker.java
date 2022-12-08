package tracker;

public class Tracker {
    public void start() {
        StandardData.addStandardData();
        Message.printMessage(Message.TITLE);
        processInput();
    }

    public void processInput() {
        do {
            MenuPrinter.printMenu(InputParser.parseInput());
        } while (true);
    }
}
