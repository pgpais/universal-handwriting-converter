package pt.up.hs.uhc.handspy.legacy;

import protocol.*;
import pt.up.hs.uhc.PageWriter;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.OutputStream;

/**
 * Writer for HandSpy legacy format.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyLegacyWriter implements PageWriter {

    @Override
    public void write(Page page, OutputStream os) throws Exception {

        // convert
        Protocol protocol = new Protocol();

        if (page.getMetadata("id") != null) {
            protocol.setCode((String) page.getMetadata("id"));
        }

        Header header = new Header();

        Subject subject = new Subject();
        subject.setBirthdate(new Birthdate());
        header.setSubject(subject);
        header.setSchool(new School());

        Layout layout = new Layout();
        if (page.getMetadata("noteType") != null) {
            layout.setLayout(String.valueOf(page.getMetadata("noteType")));
        }
        if (page.getMetadata("pageNo") != null) {
            layout.setPage(Integer.parseInt(String.valueOf(page.getMetadata("pageNo"))));
        }
        header.setLayout(layout);

        protocol.setHeader(header);

        page.getStrokes()
                .forEach(stroke -> protocol.getStrokes()
                        .add(buildProtocolStrokes(stroke)));

        // marshal to output stream
        try{
            // creating the JAXB context
            JAXBContext jaxbContext = JAXBContext.newInstance(Protocol.class);
            // creating the marshaller object
            Marshaller marshallObj = jaxbContext.createMarshaller();
            // setting the property to show xml format output
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // calling the marshall method
            QName qName = new QName("protocol", "protocol");
            JAXBElement<Protocol> root = new JAXBElement<>(qName, Protocol.class, protocol);
            marshallObj.marshal(root, os);
        } catch(Exception e) {
            throw new Exception("Writing HandSpy legacy page to output stream", e);
        }
    }

    private protocol.Strokes buildProtocolStrokes(Stroke stroke) {
        protocol.Strokes protocolStrokes = new protocol.Strokes();
        protocolStrokes.setStart(String.valueOf(stroke.getStartTime()));
        protocolStrokes.setStop(String.valueOf(stroke.getEndTime()));
        stroke.getDots().forEach(dot -> protocolStrokes.getStroke()
                .add(buildProtocolStroke(dot)));
        return protocolStrokes;
    }

    private protocol.Stroke buildProtocolStroke(Dot dot) {
        protocol.Stroke protocolStroke = new protocol.Stroke();
        protocolStroke.setX(dot.getX() != null ? dot.getX().intValue() : null);
        protocolStroke.setY(dot.getY() != null ? dot.getY().intValue() : null);
        protocolStroke.setTime(dot.getTimestamp());
        return protocolStroke;
    }
}
