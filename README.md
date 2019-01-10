# Kakao Maker

Auto-generating [Kakao](https://github.com/agoda-com/kakao) screens.

## Install

```groovy
// build.gradle

plugins {
    id "com.aafanasev.kakao-maker" version "<version>"
}
```

## Usage

#### 1. Configure

```groovy
// build.gradle

kakaoMaker {
    
    // Logger
    debug false
    
    // Output directory
    outputDir file("./src/androidTest/java/com/sample/app/screens")
    
    // Android application id
    applicationId "com.sample.app" 
    
    // Package name of generated screens
    packageName "com.sample.app.screens"
}
```

#### 2. Use

```xml
<!-- activity_main.xml -->

<LinearLayout
    android:id="@+id/root"
    tools:kakaoScreenName="MainScreen"
    ...>
    
    <TextView
        android:id="@+id/title"
        ...    
        />
        
    <com.sample.app.CustomSubtitle
            android:id="@+id/sub_title"
            tools:kakaoType="KTextView"
            ...
            />
    
    <include layout="@layout/merge"/>
        
    <include layout="@layout/include"/>
    
    <include layout="@layout/include_screen"/>
    
    <View
        android:id="@+id/dummy"
        tools:kakaoIgnore="true"
        />
        
    <Button
        android:id="@+id/submit"
        ...
        />
    
</LinearLayout>
```

```xml
<!-- merge.xml -->

<merge>
    
    <Button
        android:id="@+id/shared_btn_1"
        ...
        />
        
</merge>
```

```xml
<!-- include.xml -->

<FrameLayout
    ...>
    
    <Button
        android:id="@+id/shared_btn_2"
        ...
        />
        
</FrameLayout>
```

```xml
<!-- include_screen.xml -->

<FrameLayout
    tools:kakaoScreenName="SharedScreen"
    ...>
    
    <Button
        android:id="@+id/shared_btn_3"
        ...
        />
        
</FrameLayout>
```

#### 3. Generate

```sh
./gradlew generateKakaoScreens
```

#### 4. Result

```kotlin
// MainScreen.kt

package com.sample.app.screens

import com.sample.app.R

class MainScreen : Screen<MainScreen>() {
    val root = KView { withId(R.id.root) } 
    val title = KTextView { withId(R.id.title) }
    val subTitle = KTextView { withId(R.id.sub_title) }
    val sharedBtn1 = KButton { withId(R.id.shared_btn_1) }
    val sharedBtn2 = KButton { withId(R.id.shared_btn_2) }
    val sharedScreen = SharedScreen()
    val subTitle = KTextView { withId(R.id.sub_title) }
    val submit = KButton { withId(R.id.submit) }
}
```

```kotlin
// SharedScreen.kt

package com.sample.app.screens

import com.sample.app.R
class SharedScreen : Screen<SharedScreen>() {
    val sharedBtn3 = KButton { withId(R.id.shared_btn_3) }
}
```
