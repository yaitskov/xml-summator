package net.sf.dan.xmlsummator;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Daneel Yaitskov
 */
public class XmlSummatorTest {

    @Test
    public void testProcess() throws ParserConfigurationException, IOException,
            SAXException, DtdException, XPathExpressionException {
        XmlSummator summator = new XmlSummator();

        Assert.assertEquals(new BigInteger("0"), summator.getTotalSum());
        Assert.assertEquals(0, summator.getTotalSum().intValue());

        summator.process(EntryPoint.getXmlDocument(IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<sum>\n" +
                "    <summand value=\"2\" />\n" +
                "</sum>")));
        Assert.assertEquals(new BigInteger("2"), summator.getTotalSum());


        summator.process(EntryPoint.getXmlDocument(IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<sum>\n" +
                "</sum>")));
        Assert.assertEquals(new BigInteger("2"), summator.getTotalSum());

        summator.setTotalSum(new BigInteger("0"));
        Assert.assertEquals(new BigInteger("0"), summator.getTotalSum());

        summator.process(EntryPoint.getXmlDocument(IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<sum>\n" +
                "    <summand value=\"1\" />\n" +
                "    <summand value=\"10\" />\n" +
                "    <summand value=\"100\" />\n" +
                "    <summand value=\"1000\" />\n" +
                "    <summand value=\"10000\" />\n" +
                "    <summand value=\"100000\" />\n" +
                "    <summand value=\"1000000\" />\n" +
                "</sum>")));

        Assert.assertEquals(new BigInteger("1111111"), summator.getTotalSum());

    }
}
