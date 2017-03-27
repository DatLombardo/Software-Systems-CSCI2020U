package sample;

import java.io.File;

/* Hint:  This could be a useful type for your tree view */
public class ProjectFile {
    private File file;

    public ProjectFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String toString() {
        return this.file.getName();
    }
}
