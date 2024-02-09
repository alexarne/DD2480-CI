package com.group21.ci;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;

public class RepositoryTester {
    private String URL;
    private String SHA;
    private String branch;
    private String owner;
    private String repositoryName;

    public RepositoryTester(RepositoryInfo repo) {
        this.owner = repo.owner;
        this.repositoryName = repo.name;
        this.URL = repo.cloneUrl;
        this.SHA = repo.commitId;
        this.branch = repo.ref;
    }
    
    public void runTests() {
        StatusSender statusSender = new StatusSender(owner, repositoryName, SHA);
        statusSender.sendPendingStatus();
        String id = generateUniqueIdentifier();
        String dir = Config.DIRECTORY_REPOSITORIES + id;
        File logFile = new File(Config.DIRECTORY_BUILD_HISTORY + id + "/" + Config.BUILD_LOG_FILENAME);
        File SHAFile = new File(Config.DIRECTORY_BUILD_HISTORY + id + "/" + Config.BUILD_IDENTIFIER_FILENAME);
        File branchFile = new File(Config.DIRECTORY_BUILD_HISTORY + id + "/" + Config.BUILD_BRANCH_FILENAME);
        logFile.getParentFile().mkdirs();
        
        // Clone and run test.sh
        int exitCode = -99;
        try {
            logFile.createNewFile();
            SHAFile.createNewFile();
            branchFile.createNewFile();
            ProcessBuilder process = new ProcessBuilder("git", "clone", URL, dir);
            process.redirectErrorStream(true);
            process.redirectOutput(Redirect.appendTo(logFile));
            process.redirectError(Redirect.appendTo(logFile));
            process.start().waitFor();
            process.directory(new File(dir));
            process.command("ls");
            process.start().waitFor();
            process.command("./test.sh");
            exitCode = process.start().waitFor();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            try {
                FileOutputStream fs = new FileOutputStream(logFile, true);
                PrintStream ps = new PrintStream(fs);
                e.printStackTrace(ps);
                ps.close();
                fs.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        if (exitCode == 0) {
            statusSender.sendSuccessStatus();
        } else {
            statusSender.sendFailureStatus();
        }
        
        // Delete repo regardless
        try {
            ProcessBuilder process = new ProcessBuilder("rm", "-rf", id);
            process.directory(new File(Config.DIRECTORY_REPOSITORIES));
            process.start().waitFor();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        appendToFile(SHA, SHAFile);
        appendToFile(branch, branchFile);
        appendToFile(id + ": Exit code " + exitCode, logFile);
        System.out.println(id + ": Exit code " + exitCode);
    }

    private void appendToFile(String data, File file) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String generateUniqueIdentifier() {
        return "" + System.currentTimeMillis();
    }
}
