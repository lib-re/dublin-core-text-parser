package dc_metadata;

public class Format extends Element{

    private static String EXTENT        = "spatial";
    private static String MEDIUM        = "medium";

    private static String AUDIO         = "audio";
    private static String CARRIEREXTENT = "carrierExtent";
    private static String CONTENT       = "content";
    private static String DURATION      = "duration";
    private static String IMAGEFORMAT   = "imageFormat";
    private static String IMAGERES      = "imageResolution";
    private static String MEDIATYPE     = "mediaType";
    private static String MIMETYPE      = "mimetype";
    private static String ORIGINAL      = "original";
    private static String WORKFORMAT    = "workFormat";

    private Format(){
        uri="http://purl.org/dc/elements/1.1/format";
        name="format";
        label="Format";
        definition="The file format, physical medium, or dimensions of the resource.";
        encoding="IMT";
    }

    public static Format createFormat(String qualifierText, String value){
        Format a = new Format();
        a.value = value;
        a.qualifier = determineQualifier(qualifierText);
        return a;
    }

    private static String determineQualifier(String str){

        str=str.toLowerCase();

        if      (str.contains("extent")){
          return str.contains("carrier")? CARRIEREXTENT: EXTENT;  }
        else if (str.contains("medium"))    {   return MEDIUM;    }
        else if (str.contains("audio"))     {   return AUDIO;     }
        else if (str.contains("content"))   {   return CONTENT;   }
        else if (str.contains("image")){
              if(str.contains("format"))    { return IMAGEFORMAT; }
         else if(str.contains("resolution")){ return IMAGERES;    }
         else{ return ""; }
        }else if (str.contains("mimetype")){ return MIMETYPE;     }
        else if (str.contains("original")){ return ORIGINAL;      }
        else if (str.contains("duration")){ return DURATION;      }
        else if (str.contains("mediatype")){ return MEDIATYPE;    }
        else if (str.contains("workformat")){ return WORKFORMAT;  }
        else { return ""; }
    }

}
