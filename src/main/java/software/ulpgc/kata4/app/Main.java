package software.ulpgc.kata4.app;

import software.ulpgc.kata4.commands.ImportCommand;

import java.io.File;

import static software.ulpgc.kata4.io.CsvDownloader.download;

public class Main {
    public static void main(String[] args) {

        File path = new File("");
        String Path = path.getAbsolutePath();
        String urlInput= "https://raw.githubusercontent.com/Carlosqchao/Resources/refs/heads/main/pokedex_(Update.04.20).csv";
        String urlOutputPath = Path+"\\pokedex_(Update.04.20).csv";
        download(urlInput, urlOutputPath);
        String imageUrlInput= "https://w7.pngwing.com/pngs/272/921/png-transparent-pokemon-pokeball-pokemon-go-pokemon-pokeball-thumbnail.png";
        String imageOutputPath = Path+"\\pokemon-1536849_1280.png";
        download(imageUrlInput,imageOutputPath);
        ImportCommand command = new ImportCommand();
        command.executeWriting();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);


    }
}
