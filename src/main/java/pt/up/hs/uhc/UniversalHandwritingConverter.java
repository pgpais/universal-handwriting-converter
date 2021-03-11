package pt.up.hs.uhc;

import pt.up.hs.uhc.base.PageWriter;
import pt.up.hs.uhc.exceptions.UniversalHandwritingConverterException;
import pt.up.hs.uhc.exceptions.UnknownFormatException;
import pt.up.hs.uhc.exceptions.UnsupportedFormatException;
import pt.up.hs.uhc.handspy.HandSpyReader;
import pt.up.hs.uhc.handspy.HandSpyWriter;
import pt.up.hs.uhc.handspy.legacy.HandSpyLegacyReader;
import pt.up.hs.uhc.handspy.legacy.HandSpyLegacyWriter;
import pt.up.hs.uhc.inkml.InkMLReader;
import pt.up.hs.uhc.inkml.InkMLWriter;
import pt.up.hs.uhc.lspdf.LsPDFReader;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Format;
import pt.up.hs.uhc.models.Rect;
import pt.up.hs.uhc.neonotes.NeoNotesReader;
import pt.up.hs.uhc.svg.SvgWriter;
import pt.up.hs.uhc.utils.FilenameUtils;
import pt.up.hs.uhc.utils.PageUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * API of the Universal Handwriting Converter.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UniversalHandwritingConverter {

    // readers
    private final HandSpyReader handSpyReader = new HandSpyReader();
    private final HandSpyLegacyReader handSpyLegacyReader = new HandSpyLegacyReader();
    private final InkMLReader inkMLReader = new InkMLReader();
    private final LsPDFReader lsPDFReader = new LsPDFReader();
    private final NeoNotesReader neoNotesReader = new NeoNotesReader();

    // writers
    private final HandSpyWriter handSpyWriter = new HandSpyWriter();
    private final HandSpyLegacyWriter handSpyLegacyWriter = new HandSpyLegacyWriter();
    private final InkMLWriter inkMLWriter = new InkMLWriter();
    private final SvgWriter svgWriter = new SvgWriter();

    // format
    private Format inFormat = null;
    private Format outFormat = Format.HANDSPY;

    // pages
    private final List<Page> pages = new ArrayList<>();

    public UniversalHandwritingConverter() {
    }

    public UniversalHandwritingConverter(
            Format inFormat, Format outFormat
    ) {
        this.inFormat = inFormat;
        this.outFormat = outFormat;
    }

    public UniversalHandwritingConverter inputFormat(Format inFormat) {
        this.inFormat = inFormat;
        return this;
    }

    public UniversalHandwritingConverter outputFormat(Format outFormat) {
        this.outFormat = outFormat;
        return this;
    }

    public UniversalHandwritingConverter file(File file) {
        try {
            return file(file.getAbsolutePath(), new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new UniversalHandwritingConverterException("File not found.");
        }
    }

    public UniversalHandwritingConverter file(String filename, InputStream is) {
        read(filename, is);
        return this;
    }

    public UniversalHandwritingConverter page(Page page) {
        this.pages.add(page);
        return this;
    }

    public UniversalHandwritingConverter write(OutputStream os) {
        return write(pages.size() - 1, os);
    }

    public UniversalHandwritingConverter write(int pageNr, OutputStream os) {

        if (outFormat == null) {
            outFormat = Format.HANDSPY;
        }

        try {
            switch (outFormat) {
                case NEONOTES_ARCHIVE:
                case NEONOTES:
                    throw new UnsupportedFormatException();
                case HANDSPY_LEGACY:
                    writePage(pageNr, os, handSpyLegacyWriter);
                    break;
                case INKML:
                    writePage(pageNr, os, inkMLWriter);
                    break;
                case HANDSPY:
                    writePage(pageNr, os, handSpyWriter);
                    break;
                case SVG:
                    writePage(pageNr, os, svgWriter);
                    break;
                default:
                    throw new UnknownFormatException();
            }
        } catch (UniversalHandwritingConverterException e) {
            throw e;
        } catch (Exception e) {
            throw new UniversalHandwritingConverterException("Could not write file.", e);
        }

        return this;
    }

    public UniversalHandwritingConverter center() {

        /*for (Page page: pages) {

            // calculate page center
            double pcx = page.getWidth() / 2;
            double pcy = page.getHeight() / 2;

            // calculate bounding rect center
            Rect boundingRect = PageUtils.getBoundingRect(page);
            double bcx = boundingRect.getX1() + (boundingRect.getWidth() / 2);
            double bcy = boundingRect.getY1() + (boundingRect.getHeight() / 2);

            // calculate translate values
            double dx = pcx - bcx;
            double dy = pcy - bcy;

            PageUtils.translate(page, dx, dy);
        }*/

        return this;
    }

    public UniversalHandwritingConverter normalize(boolean time) {
        return normalize(time, -1);
    }

    public UniversalHandwritingConverter normalize(boolean time, int decimalPlaces) {

        for (Page page: pages) {
            if (time) {
                PageUtils.normalizeTime(page);
            }
            if (decimalPlaces >= 0) {
                PageUtils.normalize(page, decimalPlaces);
            }
        }

        return this;
    }

    public UniversalHandwritingConverter scale(double s) {

        for (Page page: pages) {
            PageUtils.scale(page, s);
        }

        return this;
    }

    public Page getPage() {
        if (pages.isEmpty()) {
            throw new UniversalHandwritingConverterException("No pages read yet.");
        }
        return pages.get(pages.size() - 1);
    }

    public List<Page> getPages() {
        return pages;
    }

    private Format autoDetectFormat(String filename) {

        String ext = FilenameUtils.getFileExtension(filename);

        Format format;
        if (ext.matches("(?i)^(neonotes)(\\.zip)?$")) {
            format = Format.NEONOTES_ARCHIVE;
        } else if (ext.matches("(?i)^(data)$")) {
            format = Format.NEONOTES;
        } else if (ext.matches("(?i)^(xml)$")) {
            format = Format.HANDSPY_LEGACY;
        } else if (ext.matches("(?i)^(ink|inkml)$")) {
            format = Format.INKML;
        } else if (ext.matches("(?i)^(pdf)$")) {
            format = Format.LIVESCRIBE_PDF;
        } else if (ext.matches("(?i)^(json)$")) {
            format = Format.HANDSPY;
        } else {
            throw new UnsupportedFormatException();
        }
        return format;
    }

    private void read(String filename, InputStream is) {

        if (filename == null || is == null) {
            throw new UniversalHandwritingConverterException("File not specified.");
        }

        Format format;
        if (inFormat == null) {
            format = autoDetectFormat(filename);
        } else {
            format = inFormat;
        }

        try {
            switch (format) {
                case NEONOTES_ARCHIVE:
                    pages.addAll(neoNotesReader.readArchive(filename, is));
                    break;
                case NEONOTES:
                    pages.add(neoNotesReader.readSingle(is));
                    break;
                case HANDSPY_LEGACY:
                    pages.add(handSpyLegacyReader.readSingle(is));
                    break;
                case INKML:
                    pages.add(inkMLReader.readSingle(is));
                    break;
                case LIVESCRIBE_PDF:
                    pages.addAll(lsPDFReader.read(is));
                    break;
                case HANDSPY:
                    pages.add(handSpyReader.readSingle(is));
                    break;
                default:
                    throw new UnknownFormatException();
            }
        } catch (UniversalHandwritingConverterException e) {
            throw e;
        } catch (Exception e) {
            throw new UniversalHandwritingConverterException("Could not read file.", e);
        }
    }

    private void writePage(int pageNr, OutputStream os, PageWriter writer) throws Exception {
        if (pages.isEmpty() || pageNr < 0 || pageNr >= pages.size()) {
            throw new UniversalHandwritingConverterException("No such page.");
        }
        writer.writeSingle(pages.get(pageNr), os);
    }
}
