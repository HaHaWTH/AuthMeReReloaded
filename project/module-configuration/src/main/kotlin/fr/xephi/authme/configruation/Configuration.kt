package fr.xephi.authme.configruation

import java.io.File
import java.io.InputStream
import java.io.Reader

/**
 * Configuration
 *
 * @author Taboolib
 * @since 2024/7/10 19:30
 */
interface Configuration : ConfigurationSection {

    /**
     * 文件
     */
    var file: File?

    /**
     * 保存为字符串
     */
    fun saveToString(): String

    /**
     * 保存到文件
     *
     * @param file 文件
     */
    fun saveToFile(file: File? = null)

    /**
     * 从文件加载
     *
     * @param file 文件
     */
    fun loadFromFile(file: File)

    /**
     * 从字符串加载
     *
     * @param contents 字符串
     */
    fun loadFromString(contents: String)

    /**
     * 从 [Reader] 加载
     *
     * @param reader 输入流
     */
    fun loadFromReader(reader: Reader)

    /**
     * 从 [InputStream] 加载
     *
     * @param inputStream 输入流
     */
    fun loadFromInputStream(inputStream: InputStream)

    /**
     * 重载
     */
    fun reload()

    /**
     * 注册重载回调
     *
     * @param runnable 回调
     */
    fun onReload(runnable: Runnable)

    /**
     * 变更类型
     *
     * @param type 类型
     */
    fun changeType(type: Type)

    companion object {

        /**
         * 识别可能的 [ConfigurationSection] 类型
         */
        fun parse(any: Any, type: Type = Type.YAML, concurrent: Boolean = true): ConfigurationSection {
            val unwrapped = ConfigSection.unwrap(any)
            if (unwrapped is Map<*, *>) {
                return fromMap(unwrapped, type, concurrent)
            }
            return empty(type, concurrent)
        }

        /**
         * 创建空配置
         *
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [Configuration]
         */
        fun empty(type: Type = Type.YAML, concurrent: Boolean = true): Configuration {
            return ConfigFile(
                if (concurrent) type.newFormat().createConcurrentConfig() else type.newFormat()
                    .createConfig { LinkedHashMap() })
        }

        /**
         * 从文件加载
         *
         * @param file 文件
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [Configuration]
         */
        fun loadFromFile(file: File, type: Type? = null, concurrent: Boolean = true): Configuration {
            val format = (type ?: getTypeFromFile(file)).newFormat()
            val configFile =
                ConfigFile(if (concurrent) format.createConcurrentConfig() else format.createConfig { LinkedHashMap() })
            configFile.loadFromFile(file)
            return configFile
        }

        /**
         * 从 [Reader] 加载
         *
         * @param reader Reader
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [Configuration]
         */
        fun loadFromReader(reader: Reader, type: Type = Type.YAML, concurrent: Boolean = true): Configuration {
            val format = type.newFormat()
            val configFile =
                ConfigFile(if (concurrent) format.createConcurrentConfig() else format.createConfig { LinkedHashMap() })
            configFile.loadFromReader(reader)
            return configFile
        }

        /**
         * 从字符串加载
         *
         * @param contents 字符串
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [Configuration]
         */
        fun loadFromString(contents: String, type: Type = Type.YAML, concurrent: Boolean = true): Configuration {
            val format = type.newFormat()
            val configFile =
                ConfigFile(if (concurrent) format.createConcurrentConfig() else format.createConfig { LinkedHashMap() })
            configFile.loadFromString(contents)
            return configFile
        }

        /**
         * 从 [InputStream] 加载
         *
         * @param inputStream 输入流
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [Configuration]
         */
        fun loadFromInputStream(
            inputStream: InputStream,
            type: Type = Type.YAML,
            concurrent: Boolean = true
        ): Configuration {
            val format = type.newFormat()
            val configFile =
                ConfigFile(if (concurrent) format.createConcurrentConfig() else format.createConfig { LinkedHashMap() })
            configFile.loadFromInputStream(inputStream)
            return configFile
        }

        /**
         * 从 Map 加载 [ConfigurationSection]
         *
         * @param map [Map]
         * @param type 类型
         * @param concurrent 是否支持并发
         * @return [ConfigurationSection]
         */
        fun fromMap(map: Map<*, *>, type: Type = Type.YAML, concurrent: Boolean = true): ConfigurationSection {
            val empty = empty(type, concurrent)
            map.forEach { (k, v) -> empty[k.toString()] = v }
            return empty
        }

        /**
         * 从文件获取类型
         *
         * @param file 文件
         * @param def 默认类型
         * @return [Type]
         */
        fun getTypeFromFile(file: File, def: Type = Type.YAML): Type {
            return getTypeFromExtension(file.extension, def)
        }

        /**
         * 从文件扩展名获取类型
         *
         * @param extension 扩展名
         * @param def 默认类型
         * @return [Type]
         */
        fun getTypeFromExtension(extension: String, def: Type = Type.YAML): Type {
            return when (extension) {
                "yaml", "yml" -> Type.YAML
//                "toml", "tml" -> Type.TOML
//                "json" -> Type.JSON
//                "conf" -> Type.HOCON
                else -> def
            }
        }

    }

}