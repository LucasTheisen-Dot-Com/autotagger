package com.lucastheisen.autotagger.tag;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Image {
    private static Logger log = LoggerFactory.getLogger( Image.class );

    private URL url;
    private byte[] bytes;

    public Image( byte[] bytes ) {
        this.bytes = bytes;
    }

    public Image( String url ) throws MalformedURLException {
        this( new URL( url ) );
    }
    
    public Image( URL url ) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public byte[] getBytes() {
        if ( bytes == null ) {
            try {
                try ( InputStream inputStream = url.openStream()) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[16384];
                    while ( (nRead = inputStream.read( data, 0, data.length )) != -1 ) {
                        buffer.write( data, 0, nRead );
                    }
                    buffer.flush();
                    bytes = buffer.toByteArray();
                }
            }
            catch ( MalformedURLException murle ) {
                log.warn( "Malformed image url: {}", url );
            }
            catch ( Exception e ) {
                log.warn( "Unable to read from {}: {}", url, e );
            }
        }
        return bytes;
    }
    
    public void setUrl( String url ) throws MalformedURLException {
        this.url = new URL( url );
    }

    public void setUrl( URL url ) {
        this.url = url;
        this.bytes = null;
    }

    public void setBytes( byte[] bytes ) {
        this.bytes = bytes;
    }
}
