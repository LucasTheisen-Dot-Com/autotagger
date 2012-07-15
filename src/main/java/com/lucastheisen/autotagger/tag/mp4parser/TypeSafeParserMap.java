package com.lucastheisen.autotagger.tag.mp4parser;

import java.util.List;
import java.util.Map;


import com.coremedia.iso.boxes.Box;

class TypeSafeParserMap {
    private Map<Class<?>, Parser<?>> unsafeMap;
    
    public <T extends Box> void add( Class<T> clazz, Parser<T> parser ) {
        unsafeMap.put( clazz, parser );
    }
    
    public <T extends Box> void addAll( List<ParserMapEntry<T>> list ) {
        for ( ParserMapEntry<T> entry : list ) {
            unsafeMap.put( entry.clazz, entry.parser );
        }
    }
    
    public <T extends Box> boolean containsKey( Class<T> clazz ) {
        return unsafeMap.containsKey( clazz );
    }
    
    @SuppressWarnings( "unchecked" )
    public <T extends Box> Parser<T> get( Class<T> clazz ) {
        return (Parser<T>) unsafeMap.get( clazz );
    }
    
    public class ParserMapEntry<T extends Box> {
        private Class<T> clazz;
        private Parser<T> parser;
        
        public ParserMapEntry( Class<T> clazz, Parser<T> parser ) {
            this.clazz = clazz;
            this.parser = parser;
        }
    }
}