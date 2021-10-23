import java.io.File;

public class Producer implements Runnable{
    private final SyncList list; //The list of directories scanned directories
    private final File givenPath;

    public Producer(SyncList directoryList, String path){
        this.list = directoryList;
        this.givenPath = new File(path);
    }

    @Override
    public void run() {
        assert_path(this.givenPath); //check if given path exists and if it's a directory

        list.push(this.givenPath); //our directories list begins with the starting directory

        try {
            scandir(list, givenPath); //begin to recursively scan directory and sub-directories at the given path
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        list.setCompleted(); //set execution flag to 'completed'
    }

    private void scandir(SyncList list, File path) throws InterruptedException {
        File[] files = path.listFiles();
        if (files == null) {
            return;
        }
        for(File F : files){
            if(F.isDirectory()){
                list.push(F);
                scandir(list, F);
            }
        }
    }

    public static void assert_path(File F){
        if(!F.exists()){
            System.out.printf("%s the given path is not valid\n", F.getAbsolutePath());
            System.exit(-1); //Exit unsuccessful due to caught exception
        }
        if(!F.isDirectory()){
            System.out.printf("%s is not a directory\n", F.getAbsolutePath());
            System.exit(-1); //Exit unsuccessful due to caught exception
        }
    }
}