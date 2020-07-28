package pt.up.hs.uhc.base;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import pt.up.hs.uhc.models.Page;
import pt.up.hs.uhc.utils.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

/**
 * Base {@link ArchiveReader} to infer archive format from extension.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public abstract class BaseArchiveReader implements ArchiveReader {

    @Override
    public List<Page> readArchive(String filename, InputStream is) throws Exception {

        String ext = FilenameUtils.getFileExtension(filename);

        if (ext.matches("(?i)(tar\\.gz)$")) {
            GZIPInputStream gzis = new GZIPInputStream(is);
            return readArchive(new TarArchiveInputStream(gzis));
        }

        if (ext.matches("(?i)(tar)$")) {
            return readArchive(new TarArchiveInputStream(is));
        }

        return readArchive(new ZipInputStream(is));
    }

    @Override
    public List<Page> readArchive(File file) throws Exception {
        return readArchive(file.getName(), new FileInputStream(file));
    }
}
