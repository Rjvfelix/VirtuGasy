package top.niunaijun.virtugasy.view.xp

import androidx.lifecycle.MutableLiveData
import top.niunaijun.virtugasy.bean.XpModuleInfo
import top.niunaijun.virtugasy.data.XpRepository
import top.niunaijun.virtugasy.view.base.BaseViewModel

/**
 *
 * @Description:
 * @Author: wukaicheng
 * @CreateDate: 2021/5/2 20:55
 */
class XpViewModel(private val repo:XpRepository):BaseViewModel() {

    val appsLiveData = MutableLiveData<List<XpModuleInfo>>()

    val resultLiveData = MutableLiveData<String>()

    fun getInstalledModule() {
        launchOnUI {
            repo.getInstallModules(appsLiveData)
        }
    }

    fun installModule(source:String) {
        launchOnUI {
            repo.installModule(source,resultLiveData)
        }
    }


    fun unInstallModule(packageName: String){
        launchOnUI {
            repo.unInstallModule(packageName,resultLiveData)
        }
    }
}