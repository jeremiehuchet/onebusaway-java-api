/* 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.dudie.junit.rules;

import java.net.URL;

import org.junit.rules.ExternalResource;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.resource.Resource;

/**
 * Junit rule to start a webserver.
 * 
 * @author Alex Nederlof
 * @author Jeremie Huchet
 * @see http://alex.nederlof.com/blog/2012/12/19/clean-test-classes-using-junits-classrules/
 */
public class RunWithWebServer extends ExternalResource {

    private final Resource resource;

    private int port;
    private URL url;
    private Server server;

    /**
     * @param classPathResource
     *            The name of the resource. This resource must be on the test or regular classpath.
     */
    public RunWithWebServer(String classPathResource) {
        resource = Resource.newClassPathResource(classPathResource);
    }

    @Override
    protected void before() throws Throwable {
        server = new Server(0);
        final ResourceHandler handler = new ResourceHandler();
        handler.setAliases(true);
        handler.setBaseResource(resource);
        server.setHandler(handler);
        server.start();
        this.port = server.getConnectors()[0].getLocalPort();
        this.url = new URL("http", "localhost", port, "/");
    }

    @Override
    protected void after() {
        try {
            if (server != null) {
                server.stop();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not stop the server", e);
        }
    }

    public URL getUrl() {
        return url;
    }
}