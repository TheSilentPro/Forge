package tsp.forge.util;

import org.bukkit.Color;
import tsp.forge.Forge;
import tsp.smartplugin.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {

    public static String translate(String s) {
        return StringUtils.colorize(StringUtils.hex(s));
    }

    /**
     * Match a {@link Color} from a string using reflection.
     *
     * @param s The text to match it for
     * @return Color if present
     */
    public static Optional<Color> match(String s) {
        Color color = null;
        Field[] fields = Color.class.getFields();
        for (Field field : fields) {
            try {
                if (field.getName().equalsIgnoreCase(s)) {
                    color = (Color) field.get(null);
                }
            } catch (IllegalAccessException e) {
                Forge.getInstance().getLog().debug("Could not access color field for: " + field.getName());
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(color);
    }

    public static List<String> getColorsAsStrings() {
        return Arrays.stream(Color.class.getFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    public static final List<String> LIST_ADD_REMOVE = Arrays.asList(
            "add",
            "remove"
    );

    public static final List<String> LIST_BOOK = Arrays.asList(
            "title",
            "author",
            "gen"
    );

    public static final List<String> LIST_COLOR = Arrays.asList(
            "add",
            "remove",
            "color"
    );

}
