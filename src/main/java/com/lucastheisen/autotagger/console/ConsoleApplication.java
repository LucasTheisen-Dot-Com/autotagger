package com.lucastheisen.autotagger.console;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;


import com.lucastheisen.autotagger.tag.JaudiotaggerReader;
import com.lucastheisen.autotagger.tag.JaudiotaggerWriter;
import com.lucastheisen.autotagger.tag.Reader;
import com.lucastheisen.autotagger.tag.Repository;
import com.lucastheisen.autotagger.tag.TagChimpRepository;
import com.lucastheisen.autotagger.tag.TagInfo;
import com.lucastheisen.autotagger.tag.Writer;


public class ConsoleApplication {
    private Reader reader;
    private Repository repository;
    private Writer writer;

    private ConsoleApplication( Repository repository, Reader reader,
            Writer writer ) {
        this.repository = repository;
        this.reader = reader;
        this.writer = writer;
    }

    private void editTagInfo( TagInfo selectedTagInfo ) {
        selectedTagInfo.setAmazonAsin( getTagInfoAttributeEdit( "Identifier - amazon", selectedTagInfo.getAmazonAsin() ) );
        selectedTagInfo.setImdbId( getTagInfoAttributeEdit( "Identifier - imdb", selectedTagInfo.getImdbId() ) );
        selectedTagInfo.setITunesUrl( getTagInfoAttributeEdit( "Identifier - itunes", selectedTagInfo.getITunesUrl() ) );
        selectedTagInfo.setNetflixId( getTagInfoAttributeEdit( "Identifier - netflix", selectedTagInfo.getNetflixId() ) );
        selectedTagInfo.setTagChimpId( getTagInfoAttributeEdit( "Identifier - tagChimp", selectedTagInfo.getTagChimpId() ) );
        selectedTagInfo.setTitle( getTagInfoAttributeEdit( "Title", selectedTagInfo.getTitle() ) );
        selectedTagInfo.setReleaseDate( getTagInfoAttributeEdit( "Release Date", selectedTagInfo.getReleaseDate() ) );
        selectedTagInfo.setGenre( getTagInfoAttributeEdit( "Genre", selectedTagInfo.getGenre() ) );
        selectedTagInfo.setRating( getTagInfoAttributeEdit( "Rating", selectedTagInfo.getRating() ) );
        selectedTagInfo.setTotalChapters( getTagInfoAttributeEdit( "Total Chapters", selectedTagInfo.getTotalChapters() ) );
        selectedTagInfo.setImageUrl( getTagInfoAttributeEdit( "Image URL", selectedTagInfo.getImageUrl() ) );
        selectedTagInfo.setShortDescription( getTagInfoAttributeEdit( "Short Description", selectedTagInfo.getShortDescription() ) );
        selectedTagInfo.setLongDescription( getTagInfoAttributeEdit( "Long Description", selectedTagInfo.getLongDescription() ) );
        selectedTagInfo.setCast( getTagInfoAttributeListEdit( "Cast", selectedTagInfo.getCast() ) );
        selectedTagInfo.setDirectors( getTagInfoAttributeListEdit( "Directors", selectedTagInfo.getDirectors() ) );
        selectedTagInfo.setProducers( getTagInfoAttributeListEdit( "Producers", selectedTagInfo.getProducers() ) );
        selectedTagInfo.setScreenWriters( getTagInfoAttributeListEdit( "Screen Writers", selectedTagInfo.getScreenWriters() ) );
    }

    private String filenameToTitle( String filename ) {
        StringBuilder titleBuilder = new StringBuilder();
        if ( filename.indexOf( '.' ) > 0 )
            filename = filename.substring( 0, filename.lastIndexOf( '.' ) );
        char c;
        boolean lastCharacterWasDigit = false;
        boolean lastCharacterWasSpace = false;
        for ( int i = 0, count = filename.length(); i < count; i++ ) {
            c = filename.charAt( i );
            if ( Character.isAlphabetic( c ) ) {
                if ( i > 0 && !lastCharacterWasSpace
                        && Character.isUpperCase( c ) ) {
                    titleBuilder.append( ' ' );
                }
                if ( Character.isDigit( c ) ) {
                    if ( i > 0 && !lastCharacterWasSpace
                            && !lastCharacterWasDigit ) {
                        titleBuilder.append( ' ' );
                    }
                    lastCharacterWasDigit = true;
                }
                else {
                    lastCharacterWasDigit = false;
                }
                lastCharacterWasSpace = false;
                titleBuilder.append( c );
            }
            else {
                if ( !lastCharacterWasSpace ) {
                    titleBuilder.append( ' ' );
                    lastCharacterWasDigit = false;
                    lastCharacterWasSpace = true;
                }
            }
        }
        return titleBuilder.toString();
    }

    @SuppressWarnings( "unused" )
    private String formatTagInfo( TagInfo tagInfo ) {
        return formatTagInfo( tagInfo, "" );
    }

    private String formatTagInfo( TagInfo tagInfo, int index ) {
        return formatTagInfo( tagInfo, index + "  " );
    }

    private String formatTagInfo( TagInfo tagInfo, String prefix ) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter( stringWriter );
        printWriter.printf( "%sIdentifiers: amazon(%s), imdb(%s) itunes(%s) netflix(%s) tagChimp(%s)\n",
                prefix, tagInfo.getAmazonAsin(), tagInfo.getImdbId(), tagInfo.getITunesUrl(),
                tagInfo.getNetflixId(), tagInfo.getTagChimpId() );
        printWriter.printf( "%sTitle: %s\n", prefix, tagInfo.getTitle() );
        printWriter.printf( "%sRelease Date: %s     Genre: %s     Rating: %s\n",
                prefix, tagInfo.getReleaseDate(), tagInfo.getGenre(), tagInfo.getRating() );
        printWriter.printf( "%sTotal Chapters: %s\n", prefix, tagInfo.getTotalChapters() );
        printWriter.printf( "%sImage: %s\n", prefix, tagInfo.getImageUrl() );
        printWriter.printf( "%sShort Description:\n%s\n", prefix, new BlockText( tagInfo.getShortDescription(), prefix
                + "      ", 70 ).toString() );
        printWriter.printf( "%sLong Description:\n%s\n", prefix, new BlockText( tagInfo.getLongDescription(), prefix
                + "      ", 70 ).toString() );
        printWriter.printf( "%sCast:\n", prefix );
        List<String> list = tagInfo.getCast();
        if ( list != null ) {
            for ( String actor : list ) {
                printWriter.printf( "%s    %s\n", prefix, actor );
            }
        }
        printWriter.printf( "%sDirectors:\n", prefix );
        list = tagInfo.getDirectors();
        if ( list != null ) {
            for ( String director : list ) {
                printWriter.printf( "%s    %s\n", prefix, director );
            }
        }
        printWriter.printf( "%sProducers:\n", prefix );
        list = tagInfo.getProducers();
        if ( list != null ) {
            for ( String producer : list ) {
                printWriter.printf( "%s    %s\n", prefix, producer );
            }
        }
        printWriter.printf( "%sScreen Writers:\n", prefix );
        list = tagInfo.getScreenWriters();
        if ( list != null ) {
            for ( String screenWriter : list ) {
                printWriter.printf( "%s    %s\n", prefix, screenWriter );
            }
        }
        printWriter.printf( "\n" );

        return stringWriter.toString();
    }

    private List<String> getTagInfoAttributeListEdit( String field,
            List<String> list ) {
        StringBuilder builder = new StringBuilder();
        if ( list != null ) {
            boolean first = true;
            for ( String item : list ) {
                if ( first ) {
                    first = false;
                }
                else {
                    builder.append( "," );
                }
                builder.append( item );
            }
        }
        String newValue = System.console().readLine( "%s (%s):", field, builder.toString() );
        return newValue.isEmpty() ? list
                : Arrays.asList( newValue.split( "," ) );
    }

    private String getTagInfoAttributeEdit( String field, String value ) {
        String newValue = System.console().readLine( "%s (%s):", field, value );
        return newValue.isEmpty() ? value : newValue;
    }

    private void loop() {
        boolean another = true;
        while ( another ) {
            String fileMessage = "";
            File file = new File( "thisfiledoesnotexistunlessyouarereallystrange.zzz" );
            while ( !file.exists() ) {
                file = new File( System.console().readLine( "%sFilename: ", fileMessage ) );
                fileMessage = "File does not exist\n\n";
            }

            TagInfo originalTagInfo = reader.read( file );
            System.console().printf( "Current Tag Info:\n%s\n", formatTagInfo( originalTagInfo, "    " ) );
            String continueString = System.console().readLine( "Continue? (yes): " );
            if ( continueString.isEmpty()
                    || continueString.equalsIgnoreCase( "yes" ) ) {

                String defaultTitle = filenameToTitle( file.getName() );
                String title = System.console().readLine( "Title (%s): ", defaultTitle );
                if ( title.isEmpty() ) title = defaultTitle;

                List<TagInfo> tagInfoList = repository.search( title, 1 );

                int i = 0;
                for ( TagInfo tagInfo : tagInfoList ) {
                    System.console().printf( formatTagInfo( tagInfo, i ) );
                    i++;
                }

                String choiceMessage = "";
                int maxChoice = i - 1;
                int choice = -1;
                while ( choice < 0 || choice > maxChoice ) {
                    String choiceString = System.console().readLine( "%sChoice (0-%d): ", choiceMessage, maxChoice );
                    choiceMessage = "Choice out of range\n\n";

                    try {
                        choice = Integer.parseInt( choiceString );
                    }
                    catch ( NumberFormatException nfe ) {
                        choiceMessage = "Invalid choice: " + nfe.getMessage()
                                + "\n\n";
                        choice = -1;
                    }
                }

                TagInfo selectedTagInfo = tagInfoList.get( choice );

                System.console().printf( "Selected Tag Info:\n%s\n", formatTagInfo( selectedTagInfo, "    " ) );
                String editString = System.console().readLine( "Edit? (no): " );
                if ( editString.equalsIgnoreCase( "yes" ) ) {
                    boolean satisfied = false;
                    while ( !satisfied ) {
                        editTagInfo( selectedTagInfo );
                        System.console().printf( "Selected Tag Info:\n%s\n", formatTagInfo( selectedTagInfo, "    " ) );
                        String satisfiedString = System.console().readLine( "Satisfied? (no): " );
                        satisfied = satisfiedString.equalsIgnoreCase( "yes" );
                    }
                }

                try {
                    writer.write( file, selectedTagInfo );
                    System.console().printf( "Tagging successful\n\n" );
                }
                catch ( Exception e ) {
                    System.console().printf( "Tagging failed: %s\n\n", e.getMessage() );
                }
            }

            String anotherString = System.console().readLine( "Another? (yes): " );
            another = (anotherString.isEmpty() || anotherString.equalsIgnoreCase( "yes" ));
        }
    }

    public static void main( String[] args ) {
        Repository repository = new TagChimpRepository( "http", "www.tagchimp.com", "/ape/search.php", "10664939374FF64C1FA0EFE" );
        Writer writer = new JaudiotaggerWriter();
        Reader reader = new JaudiotaggerReader();

        new ConsoleApplication( repository, reader, writer ).loop();
    }
}
