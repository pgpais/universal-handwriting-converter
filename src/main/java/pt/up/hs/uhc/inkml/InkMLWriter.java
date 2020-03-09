package pt.up.hs.uhc.inkml;

import org.w3._2003.inkml.*;
import pt.up.hs.uhc.PageWriter;
import pt.up.hs.uhc.models.Dot;
import pt.up.hs.uhc.models.DotType;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Stroke;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * Writer for InkML files.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class InkMLWriter implements PageWriter {

    @Override
    public void write(Page page, OutputStream os) throws Exception {

        InkType ink = new InkType();

        if (page.getMetadata("id") != null) {
            ink.setDocumentID((String) page.getMetadata("id"));
        }

        // page header
        AnnotationType widthAnnotation = new AnnotationType();
        widthAnnotation.setType("width");
        widthAnnotation.setValue(String.valueOf(page.getWidth()));
        ink.getDefinitionsOrContextOrTrace().add(widthAnnotation);

        AnnotationType heightAnnotation = new AnnotationType();
        heightAnnotation.setType("height");
        heightAnnotation.setValue(String.valueOf(page.getHeight()));
        ink.getDefinitionsOrContextOrTrace().add(heightAnnotation);

        // trace context
        ContextType traceContext = new ContextType();
        traceContext.setId("trace");

        InkSourceType inkSource = new InkSourceType();
        inkSource.setId("traceInfo");

        TraceFormatType traceFormat = new TraceFormatType();

        ChannelType xChannel = new ChannelType();
        xChannel.setName("X");
        xChannel.setType("decimal");
        traceFormat.getChannel().add(xChannel);

        ChannelType yChannel = new ChannelType();
        yChannel.setName("Y");
        yChannel.setType("decimal");
        traceFormat.getChannel().add(yChannel);

        ChannelType tChannel = new ChannelType();
        tChannel.setName("T");
        tChannel.setType("integer");
        tChannel.setUnits("ms");
        traceFormat.getChannel().add(tChannel);

        inkSource.setTraceFormat(traceFormat);

        traceContext.setInkSource(inkSource);

        ink.getDefinitionsOrContextOrTrace().add(traceContext);

        // traces
        long startTime;
        if (page.getStrokes().isEmpty()) {
            startTime = 0;
        } else {
            startTime = page.getStrokes().get(0).getStartTime();
        }
        for (Stroke stroke: page.getStrokes()) {
            ink.getDefinitionsOrContextOrTrace()
                    .add(buildTrace(startTime, stroke));
        }

        // marshal to output stream
        try{
            // creating the JAXB context
            JAXBContext jaxbContext = JAXBContext.newInstance(InkType.class);
            // creating the marshaller object
            Marshaller marshallObj = jaxbContext.createMarshaller();
            // setting the property to show xml format output
            marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // calling the marshall method
            QName qName = new QName("http://www.w3.org/2003/InkML", "ink");
            JAXBElement<InkType> root = new JAXBElement<>(qName, InkType.class, ink);
            marshallObj.marshal(root, os);
        } catch(Exception e) {
            throw new Exception("Writing InkML to output stream", e);
        }
    }

    private TraceType buildTrace(long captureStartTime, Stroke stroke) {

        TraceType trace = new TraceType();

        trace.setId((String) stroke.getMetadata("id"));
        trace.setTimeOffset(BigInteger.valueOf(stroke.getStartTime() - captureStartTime));
        trace.setDuration(
                BigInteger.valueOf(stroke.getEndTime() - stroke.getStartTime())
        );

        DotType dotType = null;

        StringBuilder traceBuilder = new StringBuilder();
        for (Dot dot: stroke.getDots()) {
            if (dotType == null) {
                dotType = dot.getType();
            } else if (!dotType.equals(dot.getType())) {
                dotType = DotType.MOVE;
            }
            traceBuilder
                    .append(dot.getX())
                    .append(' ')
                    .append(dot.getY())
                    .append(' ')
                    .append(dot.getTimestamp())
                    .append(',');
        }
        trace.setValue(traceBuilder.toString());

        if (dotType == DotType.DOWN) {
            trace.setType("penDown");
        } else if (dotType == DotType.UP) {
            trace.setType("penUp");
        } else {
            trace.setType("indeterminate");
        }

        return trace;
    }
}
