package com.group21.ci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import org.json.*;


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
        switch (request.getMethod()) {
            case "POST":
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append(System.lineSeparator());
                }
                String data = buffer.toString();
                JSONObject jsonObj = new JSONObject(data);
                String ref = jsonObj.getString("ref");
                ref = ref.substring(ref.lastIndexOf("/")+1);
                String commitId = jsonObj.getJSONObject("head_commit").getString("id");
                String cloneUrl = jsonObj.getJSONObject("repository").getString("clone_url");
                String owner = jsonObj.getJSONObject("repository").getJSONObject("owner").getString("name");

                String print = "branch: " + ref + " commit id: " + commitId + " clone url: " + cloneUrl;

                response.getWriter().println(print);
                
                break;
            case "GET":
                
                break;
        
            default:
                break;
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        String owner = "alexarne";
        String repositoryName = "DD2480-CI";
        String SHA = "qowdpinqwdoin";
        String branch = "master";
        RepositoryTester repositoryTester = new RepositoryTester(owner, repositoryName, SHA, branch);
        repositoryTester.runTests();
        Server server = new Server(Config.PORT);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
