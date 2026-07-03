# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ============================================
# ОСНОВНЫЕ ПРАВИЛА
# ============================================

# Сохраняем имена для отладки
-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# ============================================
# МОДЕЛИ ДАННЫХ (ВАЖНО!)
# ============================================

# Сохраняем все data классы и их конструкторы
-keep class com.example.diyca.data.** { *; }
-keep class com.example.diyca.domain.** { *; }
-keep class com.example.diyca.feature.** { *; }

# Сохраняем конструкторы без параметров (нужны для Gson)
-keepclassmembers class com.example.diyca.data.** {
    public <init>();
    public <init>(...);
}

# Сохраняем поля с аннотациями @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Сохраняем вложенные классы (важно для Gson)
-keep class com.example.diyca.data.**$* { *; }
-keep class com.example.diyca.domain.**$* { *; }

# ============================================
# RETROFIT
# ============================================

# Сохраняем Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes Exceptions

# Сохраняем аннотации Retrofit
-keep,allowobfuscation,allowshrinking @interface retrofit2.http.*
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Сохраняем Callback и Response
-keep,allowobfuscation,allowshrinking class retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class retrofit2.Retrofit

# Сохраняем интерфейсы API
-keep interface com.example.diyca.data.network.** { *; }
-keep class com.example.diyca.data.network.** { *; }

# ============================================
# GSON
# ============================================

# Сохраняем Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.** { *; }

# Сохраняем TypeAdapter и JsonSerializer
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Сохраняем аннотации Gson
-keepattributes AnnotationDefault
-keepattributes EnclosingMethod

# ============================================
# OKHTTP
# ============================================

# Сохраняем OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Сохраняем логирование OkHttp
-keep class okhttp3.logging.** { *; }

# ============================================
# КОРИУТИНЫ
# ============================================

# Сохраняем корутины
-keep class kotlinx.coroutines.** { *; }
-keep class kotlin.coroutines.** { *; }
-keep class kotlinx.serialization.** { *; }

# ============================================
# KOIN
# ============================================

-keep class org.koin.** { *; }
-keep class org.koin.android.** { *; }
-keep class org.koin.androidx.** { *; }

# ============================================
# ROOM
# ============================================

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# ============================================
# LOTTIE
# ============================================

-keep class com.airbnb.lottie.** { *; }

# ============================================
# ДОПОЛНИТЕЛЬНЫЕ ПРАВИЛА
# ============================================

# Сохраняем сериализуемые классы
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Не обфусцировать имена пакетов
-keepattributes Package

# Для отладки - сохраняем названия методов
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}