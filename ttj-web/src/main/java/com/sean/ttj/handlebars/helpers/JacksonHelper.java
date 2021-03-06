package com.sean.ttj.handlebars.helpers;


import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.SerializableString;
import org.codehaus.jackson.io.CharacterEscapes;
import org.codehaus.jackson.io.SegmentedStringWriter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * A Jackson 2.x helper.
 * <p>
 * Basic usage:
 * </p>
 *
 * <pre>
 *  Handlebars hbs = new Handlebars();
 *
 *  hbs.registerHelper("json", JacksonHelper.INSTANCE);
 *
 *  ...
 *
 *  {{json model}}
 * </pre>
 * <p>
 * If <code>model</code> is null an empty string is returned.
 * </p>
 * <p>
 * You can change this using the <code>default</code> option:
 * </p>
 *
 * <pre>
 *  {{json model default="{}"}}
 * </pre>
 *
 * <p>
 * Using a view class:
 * </p>
 *
 * <pre>
 *  {{json model view="foo.MyView"}}
 * </pre>
 * <p>
 * Using alias for views:
 * </p>
 *
 * <pre>
 *  {{json model view="myView"}}
 * </pre>
 *
 * <p>
 * Escape HTML chars:
 * </p>
 *
 * <pre>
 *  {{json model escapeHtml=true}}
 * </pre>
 *
 * <p>
 * Pretty printer:
 * </p>
 *
 * <pre>
 *  {{json model pretty=true}}
 * </pre>
 *
 * @author edgar.espina
 * @since 0.4.0
 */
public class JacksonHelper implements Helper<Object> {

    /**
     * Escape HTML chars from JSON content.
     * See http://www.cowtowncoder.com/blog/archives/2012/08/entry_476.html
     *
     * @author edgar.espina
     * @since 1.0.0
     */
    private static class HtmlEscapes extends CharacterEscapes {

        /**
         * The escape table.
         */
        private int[] escapeTable;
        {
            // Start with set of characters known to require escaping (double-quote, backslash etc)
            escapeTable = CharacterEscapes.standardAsciiEscapesForJSON();
            // and force escaping of a few others:
            escapeTable['<'] = CharacterEscapes.ESCAPE_STANDARD;
            escapeTable['>'] = CharacterEscapes.ESCAPE_STANDARD;
            escapeTable['&'] = CharacterEscapes.ESCAPE_STANDARD;
            escapeTable['\''] = CharacterEscapes.ESCAPE_STANDARD;
        }

        @Override
        public int[] getEscapeCodesForAscii() {
            return escapeTable;
        }

        @Override
        public SerializableString getEscapeSequence(final int ch) {
            return null;
        }

    }

    /**
     * A singleton version of {@link JacksonHelper}.
     */
    public static final Helper<Object> INSTANCE = new JacksonHelper();

    /**
     * The JSON parser.
     */
    private final ObjectMapper mapper;

    /**
     * Class alias registry.
     */
    private final Map<String, Class<?>> alias = new HashMap<String, Class<?>>();

    /**
     * Creates a new {@link JacksonHelper}.
     *
     * @param objectMapper The object's mapper. Required.
     */
    public JacksonHelper(final ObjectMapper objectMapper) {
        mapper = notNull(objectMapper, "The object mapper is required.");
    }

    /**
     * Creates a new {@link JacksonHelper}.
     */
    private JacksonHelper() {
        this(new ObjectMapper());
    }

    public CharSequence apply(final Object context, final Options options)
            throws IOException {
        if (context == null) {
            return options.hash("default", "");
        }
        String viewName = options.hash("view", "");
        JsonGenerator generator = null;
        try {
            final ObjectWriter writer;
            // do we need to use a view?
            if (!isEmpty(viewName)) {
                Class<?> viewClass = alias.get(viewName);
                if (viewClass == null) {
                    viewClass = getClass().getClassLoader().loadClass(viewName);
                }
                writer = mapper.writerWithView(viewClass);
            } else {
                writer = mapper.writer();
            }
            JsonFactory jsonFactory = mapper.getJsonFactory();

            SegmentedStringWriter output = new SegmentedStringWriter(jsonFactory._getBufferRecycler());

            // creates a json generator.
            generator = jsonFactory.createJsonGenerator(output);

            Boolean escapeHtml = options.hash("escapeHTML", Boolean.FALSE);
            // do we need to escape html?
            if (escapeHtml) {
                generator.setCharacterEscapes(new HtmlEscapes());
            }

            Boolean pretty = options.hash("pretty", Boolean.FALSE);
            if (pretty) {
                generator.setPrettyPrinter(new DefaultPrettyPrinter());
            }

            // write the JSON output.
            writer.writeValue(generator, context);

            generator.close();

            return new Handlebars.SafeString(output.getAndClear());
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(viewName, ex);
        } finally {
            if (generator != null && !generator.isClosed()) {
                generator.close();
            }
        }
    }

    /**
     * Add an alias for the given view class.
     *
     * @param alias The view alias. Required.
     * @param viewClass The view class. Required.
     * @return This helper.
     */
    public JacksonHelper viewAlias(final String alias,
                                   final Class<?> viewClass) {
        this.alias.put(notEmpty(alias, "A view alias is required."),
                notNull(viewClass, "A view class is required."));
        return this;
    }
}
