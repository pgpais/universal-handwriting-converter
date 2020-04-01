package pt.up.hs.uhc.base;

import pt.up.hs.uhc.models.Page;

import java.io.File;
import java.io.InputStream;

/**
 * Interface implemented by readers of complete pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface PageReader {

    /**
     * Reads a page of handwriting data from {@link File} file.
     *
     * @param file {@link File} page of handwriting data.
     * @return {@link Page} data from page.
     * @throws Exception if an exception occurs while reading data.
     */
    Page read(File file) throws Exception;

    /**
     * Reads a page of handwriting data, provided an {@link InputStream} of its
     * contents. For instance, in Neo Notes it would be an {@link InputStream}
     * to a "page.data" file.
     *
     * @param is {@link InputStream} to a page of handwriting data.
     * @return {@link Page} data from page.
     * @throws Exception if an exception occurs while reading data.
     */
    Page read(InputStream is) throws Exception;
}
