/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roughike.bottombar;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class BadgeCircle {
    /**
     * Creates a new circle for the Badge background.
     *
     * @param color the activeIconColor for the circle
     * @return a nice and adorable circle.
     */
    @NonNull
    static GradientDrawable make(@ColorInt int color, Context context) {
        int radius = MiscUtils.dpToPixel(context, 10);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        return drawable;
    }
}
