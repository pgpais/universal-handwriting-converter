package pt.up.hs.uhc.base;

import pt.up.hs.uhc.models.Page;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Interface implemented by writers of multiple pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface MultiPageWriter {

    /**
     * Writes pages of handwriting data, provided an {@link InputStream} of its
     * contents.
     *
     * @param is {@link InputStream} to a file with handwriting data.
     * @return {@link List} data from file.
     * @throws Exception if an exception occurs while reading data.
     */
    List<Page> read(InputStream is) throws Exception;
}
