package com.lucastheisen.autotagger.tag;


import java.io.File;
import java.io.IOException;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JaudiotaggerReader implements Reader {
    private static Logger log = LoggerFactory.getLogger( JaudiotaggerReader.class );

    @Override
    public TagInfo read( File file ) {
        log.debug( "Reading tag info from {}", file.getAbsolutePath() );
        TagInfo tagInfo = new TagInfo();

        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read( file );
        }
        catch ( CannotReadException | IOException | TagException
                | ReadOnlyFileException | InvalidAudioFrameException e ) {
            log.error( "{}: {}", e.getMessage(), e );
            throw new AutotaggerIOException( e );
        }
        Mp4Tag mp4Tag = (Mp4Tag) audioFile.getTag();
        tagInfo.setAmazonAsin( mp4Tag.getFirst( Mp4FieldKey.ASIN ) );
        //tagInfo.setCast( mp4Tag.getFirst( Mp4FieldKey.ARTIST );
        //tagInfo.setDirectors( mp4Tag.getFirst( "Director" ) );
        tagInfo.setGenre( mp4Tag.getFirst( Mp4FieldKey.GENRE ) );
        //tagInfo.setImageUrl( mp4Tag.getFirst( "IMAGE_URL" ) );
        mp4Tag.getFirst( Mp4FieldKey.ARTIST );
        mp4Tag.getFirst( Mp4FieldKey.COMPOSER );
        mp4Tag.getFirst( Mp4FieldKey.LYRICS );

        return tagInfo;
    }
}
