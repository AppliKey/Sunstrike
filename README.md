[![License](https://img.shields.io/badge/license-Apache--2.0-green.svg)](https://github.com/AppliKeySolutions/SunStrike/blob/master/LICENSE)

# SunStrike

A pretty animation for expand and collapse views with custom switch or custom seekbar.

<img src="screenshots/demo-sunstrike.gif?raw=true" alt="" width="240"/>

## Usage
### Create animation layout in xml and put your views here
```xml
        <com.applikeysolutions.library.view.AnimationLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </com.applikeysolutions.library.view.AnimationLayout>
```

### Create animation in your project with animation layout and index of view, which you want to hide
```java
        int[] indexHiddenViews = {indexFirstView, indexSecondView};
        SunStrike sunStrike = new SunStrike.SunStrikeBuilder(animationLayout, indexMainView)
                .indexHidden(indexHiddenViews)
                .build();
        sunStrike.init();
```

#### For use it with switch
```xml
    <com.applikeysolutions.library.view.CustomSwitch
        android:id="@+id/switchCompat"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```


```java
        SunStrike sunStrike = new SunStrike.SunStrikeBuilder(animation, indexMainView)
                .with(switchCompat)
                .build();
```

#### For use it with seekbar
```xml
    <com.applikeysolutions.library.view.CustomSeekBar
        android:id="@+id/seekBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```


```java
        SunStrike sunStrike = new SunStrike.SunStrikeBuilder(animation, indexMainView)
                .with(seekBar)
                .build();
```

### You can customize seekbar or switch

```java
        seekBar.setCustomColor(colorBackground, colorProgress, colorCircle);
        switchCompat.setCustomColor(colorBackground, colorProgress, colorCircle);
```

#### You can use seekbar and switch at the same time



See [sample](sample/src/main/java/com/applikeysolutions/sample/MainActivity.java).

License
-----

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.