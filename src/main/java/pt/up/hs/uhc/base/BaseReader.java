package pt.up.hs.uhc.base;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.utils.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

/**
 * Base {@link ArchiveReader} to infer archive format from extension.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public abstract class BaseReader implements PageReader, ArchiveReader {

    @Override
    public Page read(File file) throws Exception {
        return read(new FileInputStream(file));
    }

    @Override
    public List<Page> readArchive(File file) throws Exception {

        String ext = FilenameUtils.getFileExtension(file.getName());

        FileInputStream fis = new FileInputStream(file);

        if (ext.matches("(?i)(tar\\.gz)$")) {
            GZIPInputStream gzis = new GZIPInputStream(fis);
            return readArchive(new TarArchiveInputStream(gzis));
        }

        if (ext.matches("(?i)(tar)$")) {
            return readArchive(new TarArchiveInputStream(fis));
        }

        return readArchive(new ZipInputStream(fis));
    }
}
