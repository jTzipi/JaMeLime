package earth.eu.jtzipi.jamelime.data.table.converter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

/**
 * Convert a {@link Locale} to String.
 *
 * @author jTzipi
 */
@Converter(autoApply = true)
public class LocaleConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn( Locale locale ) {
        return null == locale ? null : locale.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute( String s ) {
        return null == s ? null : Locale.forLanguageTag( s );
    }
}
