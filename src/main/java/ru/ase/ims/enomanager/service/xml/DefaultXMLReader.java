package ru.ase.ims.enomanager.service.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.jaxb.JAXBHelper;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import ru.ase.ims.enomanager.model.enovia.*;
import ru.ase.ims.enomanager.model.enovia.EnoviaPolicy;
import ru.ase.ims.enomanager.model.enovia.xml.Ematrix;
import ru.ase.ims.enomanager.service.JavaParser;
import ru.ase.ims.enomanager.service.git.GitManager;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;

@Service
@Slf4j
public class DefaultXMLReader implements XMLReader {

    private final GitManager gitManager;
    private final ObjectMapper objectMapper;
    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private org.xml.sax.XMLReader xmlReader;

    public DefaultXMLReader(GitManager gitManager) {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        this.gitManager = gitManager;
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        this.objectMapper = new XmlMapper(module);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
        try {
            jaxbContext = JAXBContext.newInstance(Ematrix.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            org.xml.sax.XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setEntityResolver(new DtdEntityResolver());
            //unmarshaller.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, Boolean.TRUE);
            JAXBHelper.getUnmarshaller(unmarshaller).getXMLUnmarshaller().setEntityResolver(new DtdEntityResolver());
        } catch (JAXBException | SAXException e) {
            log.info("DefaultXMLReader with exception" + e);
        }
    }

    @Override
    public DataModel getObjectFromFile(String branchName, String fileName) {
        log.debug("FileName: " + fileName + "branchName: " + branchName);
        if (!fileName.endsWith("xml")) {
            return null;
        }
        try (InputStreamReader reader = new InputStreamReader(gitManager.getFileAsStream(branchName, fileName))) {
            return unmarshallerStream(fileName, reader);
        } catch (Exception e) {
            log.info("Create object from fileName: " + fileName + "and branchName: " + branchName + " with exception " + e);
        }
        return null;
    }

    private DataModel unmarshallerStream(String type, Reader reader) throws JAXBException {
        InputSource input = new InputSource(reader);
        Source source = new SAXSource(xmlReader, input);
        Ematrix item = (Ematrix) unmarshaller.unmarshal(source);
        if (type.startsWith("type")) {
            return new EnoviaType(item);
        } else if (type.startsWith("attribute")) {
            return new EnoviaAttribute(item);
        } else if (type.startsWith("interface")) {
            return new EnoviaInterface(item);
        } else if (type.startsWith("relationship")) {
            return new EnoviaRelationship(item);
        } else if (type.startsWith("program")) {
            return new EnoviaProgram(item);
//          List<String> methods = JavaParser.parseMethods(source);
        } else if (type.startsWith("policy")) {
              return new EnoviaPolicy(item);
        }
        return null;
    }

    @Override
    public DataModel getObjectFromString(String type, String content) {
        try (StringReader reader = new StringReader(content)) {
            return unmarshallerStream(type, reader);
        } catch (Exception e) {
            log.debug("Create object from content: " + content + " with exception " + e);
        }
        return null;
    }

    @Override
    public String getHtml(Ematrix content) {

        if (content == null) return null;

        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource xslt = new StreamSource(getClass().getClassLoader().getResourceAsStream("IMStemplatesXML.xsl"));
        try {
            Transformer transformer = tf.newTransformer(xslt);
            JAXBSource source = new JAXBSource(jaxbContext, content);
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);
            return stringWriter.toString();
        } catch (Exception e) {
            log.info("Content: type = " + content.getType() +
                    "attributeDef = " + content.getAttributeDef() + " to html with exception" + e);
        }
        return null;
    }

    @Override
    public String getXml(Ematrix content) {
        log.debug("Ematrix: " + content);
        try {
            Marshaller m = jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(content, sw);

            return sw.toString();
        } catch (Exception e) {
            log.info("Content: type = " + content.getType() +
                    "attributeDef = " + content.getAttributeDef() + "to xml with exception " + e);
        }
        return null;
    }
}
