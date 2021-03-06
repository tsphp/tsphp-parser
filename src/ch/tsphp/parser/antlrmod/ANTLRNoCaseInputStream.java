/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.parser.antlrmod;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * An ANTLRInputStream which overrides the LA method in order to have case insensitive tokens.
 */
@SuppressWarnings("checkstyle:abstractclassname")
public class ANTLRNoCaseInputStream extends ANTLRInputStream
{

    public ANTLRNoCaseInputStream(final InputStream input) throws IOException {
        super(input);
    }

    public ANTLRNoCaseInputStream(final InputStream input, final int size) throws IOException {
        super(input, size);
    }

    public ANTLRNoCaseInputStream(final InputStream input, final String encoding) throws IOException {
        super(input, encoding);
    }

    public ANTLRNoCaseInputStream(final InputStream input, final int size, final String encoding) throws IOException {
        super(input, size, encoding);
    }

    public ANTLRNoCaseInputStream(final InputStream input, final int size,
            final int readBufferSize, final String encoding) throws IOException {
        super(input, size, readBufferSize, encoding);
    }

    @Override
    @SuppressWarnings({ "PMD.MethodNamingConventions", "PMD.ShortMethodName", "checkstyle:methodname" })
    public int LA(final int lookAhead) {

        int token = super.LA(lookAhead);
        if (token != 0 && token != CharStream.EOF) {
            token = Character.toLowerCase(token);
        }
        return token;
    }
}
