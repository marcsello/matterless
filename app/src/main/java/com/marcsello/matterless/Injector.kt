package com.marcsello.matterless

import android.app.Activity
import androidx.fragment.app.Fragment


val Activity.injector: MatterlessApplicationComponent
    get() {
        return (this.applicationContext as MatterlessApplication).injector
    }

val Fragment.injector: MatterlessApplicationComponent
    get() {
        return (this.requireContext().applicationContext as MatterlessApplication).injector
    }

