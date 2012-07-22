package com.lucastheisen.autotagger.tag.mp4parser;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;


import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MetaBox;
import com.googlecode.mp4parser.util.Path;
import com.lucastheisen.autotagger.tag.TagInfo;
import com.lucastheisen.autotagger.tag.Writer;


public class IsoParserWriter implements Writer {
    public BoxWriter<MetaBox> boxWriter;
    
    public IsoParserWriter( BoxWriter<MetaBox> boxWriter ) {
        this.boxWriter = boxWriter;
    }

    @Override
    public void write( File file, TagInfo tagInfo ) {
        try (
                FileChannel rChannel = new RandomAccessFile( file, "r" ).getChannel() ;
                FileChannel wChannel = new RandomAccessFile( file, "rw" ).getChannel() ;) {
            IsoFile isoFile = new IsoFile( rChannel );
            Path path = new Path( isoFile );

            MetaBox metaBox = (MetaBox) path.getPath( "/moov[0]/udta[0]/meta[0]" );

            boxWriter.write( metaBox, tagInfo );
            // HandlerBox hdlr = (HandlerBox) Path.getPath(isoFile,
            // "/moov[0]/trak[0]/mdia[0]/hdlr[0]");
            // String nuName =
            // RandomStringUtils.randomAscii(hdlr.getName().length());
            // hdlr.setName(nuName);

            isoFile.getBox( wChannel );
        }
        catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
