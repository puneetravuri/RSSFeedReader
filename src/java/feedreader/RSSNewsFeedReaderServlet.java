package feedreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Servlet that handles the AJAX requests sent by RSS feed reader. It takes the
 * parameters, reads the appropriate RSS file and sends the custom XML content
 * containing the latest feeds.
 *
 * @author vravuri
 */
public class RSSNewsFeedReaderServlet extends HttpServlet {

    private final String NYTFeedURL_World = "http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    private final String NYTFeedURL_Business = "http://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
    private final String NYTFeedURL_Technology = "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
    private final String BBCFeedURL_World = "http://feeds.bbci.co.uk/news/world/rss.xml";
    private final String BBCFeedURL_Business = "http://feeds.bbci.co.uk/news/business/rss.xml";
    private final String BBCFeedURL_Technology = "http://feeds.bbci.co.uk/news/technology/rss.xml";
    private final String SMHFeedURL_World = "http://feeds.smh.com.au/rssheadlines/world.xml";
    private final String SMHFeedURL_Business = "http://www.smh.com.au/rssheadlines/business.xml";
    private final String SMHFeedURL_Technology = "http://feeds.smh.com.au/rssheadlines/technology.xml";
    private final String FEED_READER_FILE = "feedreader.xsl";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            // Get the feed topic and source
            String newsTopic = request.getParameter("newstopic");
            String newsSource = request.getParameter("newssource");

            // If the properties are null, print output to glassfish console and return
            if (newsTopic == null || newsSource == null) {
                System.out.println("Invalid properties sent");
                return;
            }

            // Get the appropriate XML file
            String inputXML = getXML(newsSource, newsTopic);

            // If there is no feed for the request, print output to glassfish console and return
            if (inputXML == null) {
                System.out.println("No support for the requested feed");
                return;
            }

            // Parse the XML from the host website
            URL url = new URL(inputXML);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream xml = connection.getInputStream();
            FileInputStream xsl = new FileInputStream(RSSNewsFeedReaderServlet.class.getResource(FEED_READER_FILE).toString().substring(6));

            // Direct the new DOM to a temporary file
            FileOutputStream htmlResult = new FileOutputStream("result.xml");

            // Perform the transformation using the designed .xsl file
            Source xmlDoc = new StreamSource(xml);
            Source xslDoc = new StreamSource(xsl);
            Result result = new StreamResult(htmlResult);
            TransformerFactory factory = TransformerFactory.newInstance();
            try {
                Transformer trans = factory.newTransformer(xslDoc);
                trans.transform(xmlDoc, result);
            } catch (Exception e) {
                // If any error in transformation, print error to glassfish console and return
                System.out.println("Error in transforming the feed");
                return;
            }
            htmlResult.close();

            // Read the output DOM from the temporary file
            StringBuilder xmlOutput = new StringBuilder();
            BufferedReader in = new BufferedReader(new FileReader("result.xml"));
            String str;
            while ((str = in.readLine()) != null) {
                xmlOutput.append(str);
            }

            // Set the new DOM and send it to the client
            response.setContentType("text/xml");
            response.getWriter().write(xmlOutput.toString());
        } finally {
            out.close();
        }
    }

    /**
     * This method returns the appropriate feed XML file
     *
     * @param source News Source
     * @param topic News Topic
     * @return XML file path
     */
    private String getXML(String source, String topic) {
        if ("NYT".equals(source) && "World".equals(topic)) {
            return NYTFeedURL_World;
        } else if ("NYT".equals(source) && "Business".equals(topic)) {
            return NYTFeedURL_Business;
        } else if ("NYT".equals(source) && "Technology".equals(topic)) {
            return NYTFeedURL_Technology;
        } else if ("BBC".equals(source) && "World".equals(topic)) {
            return BBCFeedURL_World;
        } else if ("BBC".equals(source) && "Business".equals(topic)) {
            return BBCFeedURL_Business;
        } else if ("BBC".equals(source) && "Technology".equals(topic)) {
            return BBCFeedURL_Technology;
        } else if ("SMH".equals(source) && "World".equals(topic)) {
            return SMHFeedURL_World;
        } else if ("SMH".equals(source) && "Business".equals(topic)) {
            return SMHFeedURL_Business;
        } else if ("SMH".equals(source) && "Technology".equals(topic)) {
            return SMHFeedURL_Technology;
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
