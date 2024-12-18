package software.ulpgc.kata4.commands;

public interface Command {
    String executeReading() throws Exception;

    void executeWriting();
}
