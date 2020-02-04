package com.processor.reader;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.processor.parser.ParseContent;

import org.apache.commons.io.FilenameUtils;

public class MonitorDirectory {
    private final String INPUT_FOLDER = "\\data\\in\\";
    private final String HOME_DIR = "user.home";
    private final String VALID_EXTENSION = "dat";
    private ParseContent parseContent;
    private FileReader fReader;

    public MonitorDirectory(){
        parseContent = new ParseContent();
        fReader = new FileReader();
    }
    /**
     * Get all new file inputs
     * @throws IOException
     * @throws InterruptedException
     */
    public void monitorDirectoryListener() throws IOException,
			InterruptedException {

        String inputFolderPath = System.getProperty(this.HOME_DIR) + this.INPUT_FOLDER;
		Path inputFolder = Paths.get(inputFolderPath);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		inputFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
                    System.out.println("File Created:" + fileName);
                    Path filePath = FileSystems.getDefault().getPath(inputFolderPath, fileName);
                    if (isValidFile(filePath)){
                        parseContent.parse(fileName, fReader.readFileContents(filePath));
                    }
                    
				}
			}
			valid = watchKey.reset();

		} while (valid);
    }

    /**
     * Check if file have correct extension and its not a folder
     * @param filePath
     * @return true or error message
     */
    public boolean isValidFile(Path filePath){
        String fileName = filePath.getFileName().toString();
        
        if (!FilenameUtils.getExtension(fileName).equals(this.VALID_EXTENSION)){
            errorMessage("Invalid format " + filePath);
        } else if (filePath.toFile().isDirectory()) {
            errorMessage("Only files allowed, not directories: " + filePath);
        }
        
        return true;
    }


    
    /**
     * Prints error message and exit program
     * @param msg
     */
    private void errorMessage(String msg) {
        System.err.println(msg);
        System.exit(1);
     }
}