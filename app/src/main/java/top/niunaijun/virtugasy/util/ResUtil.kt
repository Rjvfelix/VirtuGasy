package top.niunaijun.virtugasy.util

import androidx.annotation.StringRes
import top.niunaijun.virtugasy.app.App


fun getString(@StringRes id:Int,vararg arg:String):String{
    if(arg.isEmpty()){
        return App.getContext().getString(id)
    }
    return App.getContext().getString(id,*arg)
}

