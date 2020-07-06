package ru.ase.ims.enomanager.service.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

public class DtdEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        InputStream dtd = getClass().getClassLoader().getResourceAsStream("ematrixml.dtd");
        return new InputSource(dtd);
    }

}
