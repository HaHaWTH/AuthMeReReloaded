package fr.xephi.authme.configruation

import com.electronwill.nightconfig.core.Config
import com.electronwill.nightconfig.core.ConfigFormat
import com.electronwill.nightconfig.yaml.YamlFormat

/**
 * Type
 *
 * @author Taboolib
 * @since 2024/7/10 19:31
 */
enum class Type(private val format: () -> ConfigFormat<out Config>) {

    YAML({ YamlFormat.defaultInstance() });

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
            return entries.first { it.newFormat().javaClass == format.javaClass }
        }
    }
}