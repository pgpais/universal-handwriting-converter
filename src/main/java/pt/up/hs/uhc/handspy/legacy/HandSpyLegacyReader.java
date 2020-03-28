package pt.up.hs.uhc.handspy.legacy;

import protocol.*;
import pt.up.hs.uhc.PageReader;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Reader for HandSpy legacy format.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class HandSpyLegacyReader implements PageReader {

    @Override
    public Page read(InputStream is) throws Exception {

        // unmarshal to input stream
        Protocol protocol;
        try{
            // creating the JAXB context
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            // creating the unmarshaller object
            Unmarshaller unmarshalObj = jaxbContext.createUnmarshaller();
            // calling the unmarshal method
            JAXBElement<Protocol> protocolElement = unmarshalObj
                    .unmarshal(new StreamSource(is), Protocol.class);

            protocol = protocolElement.getValue();
        } catch(Exception e) {
            throw new Exception("Writing InkML to output stream", e);
        }

        // convert
        Page page = new Page()
                .metadata(readProtocolHeader(protocol.getHeader()));
        if (protocol.getCode() != null) {
            page.addMetadata("id", protocol.getCode());
        }

        double pageMaxX = 0D, pageMaxY = 0D;
        for (protocol.Strokes protocolStrokes: protocol.getStrokes()) {
            Stroke stroke = readStroke(protocolStrokes);
            page.addStroke(stroke);
            double maxX = stroke.getDots().parallelStream()
                    .map(Dot::getX)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder())
                    .orElse(0D);
            double maxY = stroke.getDots().parallelStream()
                    .map(Dot::getY)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder())
                    .orElse(0D);
            if (maxX > pageMaxX) {
                pageMaxX = maxX;
            }
            if (maxY > pageMaxY) {
                pageMaxY = maxY;
            }
        }

        return page.width(pageMaxX).height(pageMaxY);
    }

    private Map<String, Object> readProtocolHeader(Header header) {

        Map<String, Object> protocolHeader = new HashMap<>();

        if (header.getLayout() != null) {
            Layout layout = header.getLayout();
            if (layout.getLayout() != null) {
                protocolHeader.put("noteType", layout.getLayout());
            }
            if (layout.getPage() != null) {
                protocolHeader.put("pageNo", layout.getPage());
            }
        }

        return protocolHeader;
    }

    private Stroke readStroke(protocol.Strokes protocolStrokes) {

        Stroke stroke = new Stroke()
                .startTime(Long.parseLong(protocolStrokes.getStart()))
                .endTime(Long.parseLong(protocolStrokes.getStop()));

        protocolStrokes.getStroke()
                .forEach(protocolStroke -> {
                    stroke.addDot(new Dot(
                            protocolStroke.getX() == null ? null : (double) protocolStroke.getX(),
                            protocolStroke.getY() == null ? null : (double) protocolStroke.getY(),
                            protocolStroke.getTime()
                    ));
                });

        return stroke;
    }
}
