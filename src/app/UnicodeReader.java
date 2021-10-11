package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;

public class UnicodeReader extends Reader {

  private final InputStreamReader internalInputStreamReader;
  private final String defaultEnc;

  private static final int BOM_SIZE = 4;

  public UnicodeReader(InputStream in, String defaultEnc) throws IOException {
    this.defaultEnc = defaultEnc;

    // Read ahead four bytes and check for BOM marks. Extra bytes are unread
    // back to the stream; only BOM bytes are skipped.
    String encoding;
    byte bom[] = new byte[BOM_SIZE];
    int n, unread;

    PushbackInputStream pushbackStream = new PushbackInputStream(in, BOM_SIZE);
    n = pushbackStream.read(bom, 0, bom.length);

    if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB)
        && (bom[2] == (byte) 0xBF)) {
      encoding = "UTF-8";
      unread = n - 3;
    } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
      encoding = "UTF-16BE";
      unread = n - 2;
    } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
      encoding = "UTF-16LE";
      unread = n - 2;
    } else if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00)
        && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
      encoding = "UTF-32BE";
      unread = n - 4;
    } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)
        && (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
      encoding = "UTF-32LE";
      unread = n - 4;
    } else {
      // Unicode BOM mark not found, unread all bytes
      encoding = defaultEnc;
      unread = n;
    }
    if (unread > 0) {
      pushbackStream.unread(bom, (n - unread), unread);
    } else if (unread < -1) {
      pushbackStream.unread(bom, 0, 0);
    }

    // Use given encoding
    if (encoding == null) {
      internalInputStreamReader = new InputStreamReader(pushbackStream);
    } else {
      internalInputStreamReader = new InputStreamReader(pushbackStream,
          encoding);
    }
  }

  public String getDefaultEncoding() {
    return defaultEnc;
  }

  public String getEncoding() {
    return internalInputStreamReader.getEncoding();
  }

  @Override public void close() throws IOException {
    internalInputStreamReader.close();
  }

  @Override public int read(char[] cbuf, int off, int len) throws IOException {
    return internalInputStreamReader.read(cbuf, off, len);
  }
}