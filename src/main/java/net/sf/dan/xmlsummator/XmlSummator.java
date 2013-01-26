package net.sf.dan.xmlsummator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.math.BigInteger;

/**
 * Sums numbers from XML document.
 *
 * There is a XPath pattern for selecting numbers:
 * <pre>
 *     /sum/summand@value
 * </pre>
 *
 * Daneel Yaitskov
 */
public class XmlSummator {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSummator.class);

    private XPath xp;

    private BigInteger totalSum;

    public BigInteger getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigInteger totalSum) {
        this.totalSum = totalSum;
    }

    public XmlSummator() {
        xp = XPathFactory.newInstance().newXPath();
        totalSum = new BigInteger("0");
    }

    /**
     * Adds to the total sum sum of numbers from the specified document.
     * @param document  source of long numbers to sum.
     * @throws XPathExpressionException  {@link #totalSum} is not changed
     * @throws DtdException              {@link #totalSum} is not changed
     */
    public void process(Document document) throws XPathExpressionException, DtdException {
        NodeList sumList = (NodeList) xp.evaluate("/sum/summand", document, XPathConstants.NODESET);
        BigInteger intermediate = new BigInteger("0");
        for (int i = 0; i < sumList.getLength(); ++i) {
            String itemValue = sumList.item(i).getAttributes().getNamedItem("value").getNodeValue();
            LOGGER.debug("item {} got {}", i+1, itemValue);
            try {
                BigInteger parsedValue = new BigInteger(itemValue);
                intermediate = intermediate.add(parsedValue);
            } catch (NumberFormatException e) {
                LOGGER.error("item /sum/summand[{}]@value is not long number but '{}'", i + 1, itemValue);
                throw new DtdException("summand " + (i+1) + " invalid");
            }
        }
        LOGGER.debug("intermediate sum is {}, old total {}", intermediate, totalSum);
        totalSum = totalSum.add(intermediate);
    }
}
