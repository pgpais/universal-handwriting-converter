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
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.models.Format;
import pt.up.hs.uhc.neonotes.NeoNotesReader;
import pt.up.hs.uhc.utils.FilenameUtils;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * API of the Universal Handwriting Converter.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public class UniversalHandwritingConverter {

    // readers
    private HandSpyReader handSpyReader = new HandSpyReader();
    private HandSpyLegacyReader handSpyLegacyReader = new HandSpyLegacyReader();
    private InkMLReader inkMLReader = new InkMLReader();
    private NeoNotesReader neoNotesReader = new NeoNotesReader();

    // writers
    private HandSpyWriter handSpyWriter = new HandSpyWriter();
    private HandSpyLegacyWriter handSpyLegacyWriter = new HandSpyLegacyWriter();
    private InkMLWriter inkMLWriter = new InkMLWriter();

    // format
    private Format inFormat = null;
    private Format outFormat = Format.HANDSPY;
    private File file = null;

    // pages
    private List<Page> pages = new ArrayList<>();

    public UniversalHandwritingConverter() {
    }

    public UniversalHandwritingConverter(
            Format inFormat, Format outFormat, File file
    ) {
        this.inFormat = inFormat;
        this.outFormat = outFormat;
        this.file = file;
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
        this.file = file;
        return this;
    }

    public UniversalHandwritingConverter readAll() {

        if (file == null) {
            throw new UniversalHandwritingConverterException("File not specified.");
        }

        Format format;
        if (inFormat == null) {
            format = autoDetectFormat(file);
        } else {
            format = inFormat;
        }

        try {
            switch (format) {
                case NEONOTES_ARCHIVE:
                    pages.addAll(neoNotesReader.readArchive(file));
                    break;
                case NEONOTES:
                    pages.add(neoNotesReader.read(file));
                    break;
                case HANDSPY_LEGACY:
                    pages.add(handSpyLegacyReader.read(file));
                    break;
                case INKML:
                    pages.add(inkMLReader.read(file));
                    break;
                case HANDSPY:
                    pages.add(handSpyReader.read(file));
                    break;
                default:
                    throw new UnknownFormatException();
            }
        } catch (UniversalHandwritingConverterException e) {
            throw e;
        } catch (Exception e) {
            throw new UniversalHandwritingConverterException("Could not read file.", e);
        }

        return this;
    }

    public UniversalHandwritingConverter write(OutputStream os) {

        if (outFormat == null) {
            outFormat = Format.HANDSPY;
        }

        try {
            switch (outFormat) {
                case NEONOTES_ARCHIVE:
                case NEONOTES:
                    throw new UnsupportedFormatException();
                case HANDSPY_LEGACY:
                    writePage(os, handSpyLegacyWriter);
                    break;
                case INKML:
                    writePage(os, inkMLWriter);
                    break;
                case HANDSPY:
                    writePage(os, handSpyWriter);
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

    public Page getPage() {
        if (pages.isEmpty()) {
            throw new UniversalHandwritingConverterException("No pages read yet.");
        }
        return pages.get(pages.size() - 1);
    }

    public List<Page> getPages() {
        return pages;
    }

    private Format autoDetectFormat(File file) {

        String ext = FilenameUtils.getFileExtension(file.getName());

        Format format;
        if (ext.matches("(?i)^(neonotes)(\\.zip)?$")) {
            format = Format.NEONOTES_ARCHIVE;
        } else if (ext.matches("(?i)^(data)$")) {
            format = Format.NEONOTES;
        } else if (ext.matches("(?i)^(xml)$")) {
            format = Format.HANDSPY_LEGACY;
        } else if (ext.matches("(?i)^(ink|inkml)$")) {
            format = Format.INKML;
        } else if (ext.matches("(?i)^(json)$")) {
            format = Format.HANDSPY;
        } else {
            throw new UnsupportedFormatException();
        }
        return format;
    }

    private void writePage(OutputStream os, PageWriter writer) throws Exception {
        if (pages.isEmpty()) {
            throw new UniversalHandwritingConverterException();
        }
        writer.write(pages.get(pages.size() - 1), os);
    }
}
