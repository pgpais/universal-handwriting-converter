package pt.up.hs.uhc;

import pt.up.hs.uhc.models.Page;

import java.io.OutputStream;

/**
 * Interface implemented by writers of complete pages.
 *
 * @author José Carlos Paiva <code>josepaiva94@gmail.com</code>
 */
public interface PageWriter {

    /**
     * Writes a page of handwriting data, into an {@link OutputStream}.
     *
     * @param os {@link OutputStream} to a page of handwriting data.
     * @param page {@link Page} data from page.
     * @throws Exception if an exception occurs while writing data.
     */
    void write(Page page, OutputStream os) throws Exception;
}
