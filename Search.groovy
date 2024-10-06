class Searcher {
    //Properties
    String directory
    String search
    String replace
    String logFile
    List<String> filesList
    

    // Constructor
    Searcher(String directory, String search, String replace, String logFile) {
        this.directory = directory
        this.search = search
        this.replace = replace
        this.filesList = fileListMethod();
        this.logFile = logFile
    }

    void operate() {
        // Iterate over files based on the directory
        filesList.each { filePath ->
            if (checkIfTextExists(filePath, search)) {
                replaceFileContent(filePath)
                log(filePath);
            } 
        }
    }

    // Method for getting the list of directories
    List<String> fileListMethod() {
        List<String> filePaths = []  // Initialize an empty list to store file paths
        File dir = new File(directory)
        if (dir.exists() && dir.isDirectory()) {
            // List all files and subdirectories
            dir.eachFileRecurse { file ->
                if (file.isFile()) {
                    filePaths << file.absolutePath  // push file path to the list
                }
            }
        } else {
            println("Path directory is not valid or found ")
        }
        return filePaths // contain the list of filepaths
    }
    
    // method for checking if file contains the text
    boolean checkIfTextExists(String filePath, String searchWord) {
        File file = new File(filePath)
        String content = file.text
        return content.contains(searchWord)  // Search for the word
    }

    // method for replacing the content 
    void replaceFileContent(String filePath) {
        File file = new File(filePath)
        String content = file.text
        String newContent = content.replace(search, replace)  // Replace found words
        file.text = newContent  // write the new content back to the file
    }
    
    // method for logging
    void log(String filePath) {
        def file = logFile ? logFile : "log.txt";
        def myFile = new File(file)
        myFile.createNewFile()
        myFile.append("The directory: $filePath has been updated\n")
    }
}

// usage  : groovy Search.groovy test "search" "replace" 
Searcher searchie = new Searcher(args[0], args[1], args[2], args.length > 3 ? args[3] : null)
searchie.operate() 
