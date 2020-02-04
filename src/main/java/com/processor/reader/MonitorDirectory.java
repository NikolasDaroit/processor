package com.processor.reader;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.processor.parser.ParseContent;

import org.apache.commons.io.FilenameUtils;

public class MonitorDirectory {
    private final String INPUT_FOLDER = "C:\\Users\\nikolas.daroit\\Documents\\workspace\\input\\";
    private final String VALID_EXTENSION = "dat";
    private ParseContent parseContent;

    public MonitorDirectory(){
        parseContent = new ParseContent();
    }
    /**
     * Get all new file inputs
     * @throws IOException
     * @throws InterruptedException
     */
    public void MonitorDirectoryListener() throws IOException,
			InterruptedException {
        // TODO: handle HOMEPATH
		Path inputFolder = Paths.get(this.INPUT_FOLDER);
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
                    Path filePath = FileSystems.getDefault().getPath(this.INPUT_FOLDER, fileName);
                    if (isValidFile(filePath)){
                        parseContent.parse(filePath);
                    }
                    
				}
			}
			valid = watchKey.reset();

		} while (valid);
    }

    /**
     * 
     * @param filePath
     * @return
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
     * 
     * @param msg
     */
    private void errorMessage(String msg) {
        System.err.println(msg);
        System.exit(1);
     }
}