package net.sf.dan.xmlsummator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * This software sums long numbers from xml files.
 * Daneel Yaitskov
 */
public class EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    /**
     * counter  ( file | - )
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new InvalidUserInputException("usage: ( <path to xml file> | - )");
            }
            InputStream istream = getSourceByteStream(args[0]);
            Document document = getXmlDocument(istream);

            XmlSummator summator = new XmlSummator();
            summator.process(document);

            LOGGER.info("Total sum is {}", summator.getTotalSum());
        } catch (FileNotFoundException e) {
            error(e.getMessage(), e);
        } catch (SAXException e) {
            error("file has the syntax XML error " + e.getMessage(), e);
        } catch (IOException e) {
            error(e.getMessage(), e);
        } catch (ParserConfigurationException e) {
            error(e.getMessage(), e);
        } catch (Exception e) {
            error(e.getMessage(), e);
        }
    }

    public static void error(String message, Exception e) {
        LOGGER.error(message, e);
        //System.err.println("error: " + message);
        System.exit(1);
    }

    public static Document getXmlDocument(InputStream istream)
            throws ParserConfigurationException, IOException, SAXException {
        InputSource isource = new InputSource(istream);
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(isource);
    }

    public static InputStream getSourceByteStream(String fileName) throws FileNotFoundException {
        if (fileName.equals("-")) {
            return System.in;
        }
        return new FileInputStream(fileName);
    }
}
