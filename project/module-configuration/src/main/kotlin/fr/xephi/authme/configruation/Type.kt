package fr.xephi.authme.configruation

/**
 * Type
 *
 * @author Taboolib
 * @since 2024/7/10 19:31
 */
enum class Type(private val format: () -> ConfigFormat<out Config>) {

    YAML({ YamlFormat.INSTANCE });

//    TOML({ TomlFormat.instance() }),
//
//    JSON({ JsonFormat.emptyTolerantInstance() }),
//
//    FAST_JSON({ JsonFormat.minimalEmptyTolerantInstance() }),
//
//    HOCON({ HoconFormat.instance() });

    fun newFormat(): ConfigFormat<out Config> {
        return format()
    }

    companion object {

        fun getType(format: ConfigFormat<*>): Type {
            return values().first { it.newFormat().javaClass == format.javaClass }
        }
    }
}