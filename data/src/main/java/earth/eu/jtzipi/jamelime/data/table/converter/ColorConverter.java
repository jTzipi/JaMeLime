package earth.eu.jtzipi.jamelime.data.table.converter;

import javafx.scene.paint.Color;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert {@link javafx.scene.paint.Color} to String and vice versa.
 * @author jTzipi
 */
@Converter(autoApply = true)
public class ColorConverter implements AttributeConverter<javafx.scene.paint.Color, String> {

    /**
     * Null Color String.
     */
    public static final String NULL_COLOR = "[0,0,0,0]";
    /**
     * Null color.
     */
    public static final Color NULL_COLOR_OBJECT = Color.rgb( 0, 0, 0, 0 );
    private static final Pattern COLOR_PATTERN = Pattern.compile( "^\\[(?<red>0|0\\.[0-9]*|1\\.0|1),(?<green>0|0\\.[0-9]*|1\\.0|1),(?<blue>0|0\\.[0-9]*|1\\.0|1),(?<opacity>0\\.[0-9]*|1\\.0|1)]$" );

    @Override
    public String convertToDatabaseColumn( javafx.scene.paint.Color color ) {
        if( null == color ) {
            return NULL_COLOR;
        }

        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        double a = color.getOpacity();
        return String.format( "[%f,%f,%f,%f]", r, b,g, a );
    }

    @Override
    public javafx.scene.paint.Color convertToEntityAttribute( String s ) {
        if( null == s ) {
            return NULL_COLOR_OBJECT;
        }
        Matcher matcher = COLOR_PATTERN.matcher( s );
        if( !matcher.matches()) {
            return NULL_COLOR_OBJECT;
        }

        double red = Double.parseDouble( matcher.group( "red" ) );
        double green = Double.parseDouble( matcher.group( "green" ) );
        double blue =Double.parseDouble( matcher.group( "blue" ) );
        double opacity = Double.parseDouble( matcher.group( "opacity" ) );

        return Color.color( red,green, blue, opacity );
    }
}
