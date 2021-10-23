import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

public class Consumer implements Runnable{
    private final SyncList list; //The list of directories to scan

    public Consumer(SyncList directoryList){
        this.list = directoryList;
    }

    @Override
    //The method requires to be run inside the Main class
    public void run() {
        File file = list.pop();
        //create a string with directory name, appending lines for each file found.
        while (file != null) {
            StringBuilder sb = new StringBuilder(); //Init the string builder
            sb.append(file.getPath()).append("\n"); //append the path to the string and a new-line character
            File[] files = file.listFiles();     //check for files in the current directories
            if (files == null) {
                continue; //if the returned value is null there is no recognized directory
            }

            //create directory tree
                    // Create string of the following type
                    //      DirectoryPath
                    //          |- SubDirectory or SubFile
            Iterator<File> iterator = Arrays.stream(files).iterator(); //use to easily iterate over the collection
            while(iterator.hasNext()){
                File F = iterator.next(); //get the next file
                if(!F.isDirectory()){
                    //build tree string
                    if(iterator.hasNext()) sb.append("\t├── ").append(F.getName()).append("\n");
                    else sb.append("\t└── ").append(F.getName()).append("\n");
                }
            }
            sb.append("\n"); //then append a new-line to separate directories

            //Finally, create a string from the String Builder
            String parsedTree = sb.toString();

            //Write safely to output file
            //System.out.println("Now writing" + parsedTree); <-- Debug print
            FileCrawler.monitor.write(parsedTree);

            //Remove the scanned file right after
            file = list.pop();
        }
    }
}