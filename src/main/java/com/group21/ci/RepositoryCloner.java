package com.group21.ci;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import org.apache.commons.io.FileUtils;

public class RepositoryCloner {
    private String id;

    public RepositoryCloner() {
        this.id = generateUniqueIdentifier();
    }

    public String cloneRepository(String URL) {
        try {
            Git git = Git.cloneRepository()
                .setURI(URL)
                .setDirectory(new File(Config.DIRECTORY_REPOSITORIES + id))
                .call();
        } catch (InvalidRemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (GitAPIException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id;
    }

    public void deleteRepository() {
        try {
            FileUtils.deleteDirectory(new File(Config.DIRECTORY_REPOSITORIES + id));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String generateUniqueIdentifier() {
        return "thisshouldbeunixtime";
    }
}
