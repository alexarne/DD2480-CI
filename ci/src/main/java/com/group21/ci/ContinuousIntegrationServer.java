package com.group21.ci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;


/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        System.out.println(target);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        switch (request.getMethod()) {
            case "POST":
                
                break;
            case "GET":
                PrintWriter writer = response.getWriter();
                writer.println(getHTMLPage(target));
                break;
        
            default:
                break;
        }
    }

    /**
     * Return the appropriate HTML page for the given target URL.
     * Variants exist for build history browsing (/) and specific
     * build log viewing (/{id}).
     * @param target The target URL relative to root.
     * @return The HTML page as a string.
     */
    public String getHTMLPage(String target) {
        String htmlRespone = "<html>";
        if (target.equals("/")) {
            System.out.println("Printing build history");
            htmlRespone += "<h1>Previous Builds:</h1>";
            File file = new File(Config.DIRECTORY_BUILD_HISTORY);
            String[] directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            if (directories != null) {
                for (String directory : directories) {
                    htmlRespone += "<a href=\"/" + directory + "\"><h3>" + directory + "</h3></a>";
                }
            }
        } else if (target.matches("^/[0-9]+$")) {
            String buildId = target.substring(1);
            System.out.println("Printing build history for " + buildId);
            htmlRespone += "<h1>Build " + buildId + "</h1>";
            try {
                Scanner branchReader = new Scanner(new File(Config.DIRECTORY_BUILD_HISTORY + buildId + "/" + Config.BUILD_BRANCH_FILENAME));
                Scanner SHAReader = new Scanner(new File(Config.DIRECTORY_BUILD_HISTORY + buildId + "/" + Config.BUILD_IDENTIFIER_FILENAME));
                Scanner logReader = new Scanner(new File(Config.DIRECTORY_BUILD_HISTORY + buildId + "/" + Config.BUILD_LOG_FILENAME));
                htmlRespone += "<h2>Branch: " + branchReader.nextLine() + "</h2>";
                htmlRespone += "<h2>SHA: " + SHAReader.nextLine() + "</h2>";
                htmlRespone += "<h2>Build log:</h2>";
                while (logReader.hasNextLine()) {
                    htmlRespone += logReader.nextLine() + "<br>";
                }
                branchReader.close();
                SHAReader.close();
                logReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (!target.equals("/favicon.ico")) 
                System.out.println("Unknown get request for " + target);
        }
        htmlRespone += "</html>";
        return htmlRespone;
    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        String owner = "alexarne";
        String repositoryName = "DD2480-CI";
        String SHA = "qowdpinqwdoin";
        String branch = "master";
        RepositoryTester repositoryTester = new RepositoryTester(owner, repositoryName, SHA, branch);
        // repositoryTester.runTests();
        Server server = new Server(Config.PORT);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
