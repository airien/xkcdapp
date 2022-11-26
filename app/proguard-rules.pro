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

-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception  # Keep custom exceptions.

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# native Kotlin classes
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Mentra Companion android
-keep,includedescriptorclasses class com.mentra.companion.android.**$$serializer { *; }
-keepclassmembers class com.mentra.companion.android.** {
    *** Companion;
}
-keepclasseswithmembers class com.mentra.companion.android.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Models
-keep,includedescriptorclasses class com.mentra.models.**$$serializer { *; }
-keepclassmembers class com.mentra.models.** {
    *** Companion;
}
-keepclasseswithmembers class com.mentra.models.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keepattributes InnerClasses