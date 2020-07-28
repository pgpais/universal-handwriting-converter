package pt.up.hs.uhc.base;

import pt.up.hs.uhc.models.Page;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Interface implemented by readers of multiple pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface MultiPageReader {

    /**
     * Reads multiple pages of handwriting data from {@link File} file.
     *
     * @param file {@link File} file w/ handwriting data.
     * @return {@link List<Page>} list of page data.
     * @throws Exception if an exception occurs while reading a list of page
     * data.
     */
    List<Page> read(File file) throws Exception;

    /**
     * Reads pages of handwriting data, provided an {@link InputStream} of its
     * contents.
     *
     * @param is {@link InputStream} to a file with handwriting data.
     * @return {@link List<Page>} data from file.
     * @throws Exception if an exception occurs while reading data.
     */
    List<Page> read(InputStream is) throws Exception;
}
