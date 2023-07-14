package top.niunaijun.virtugasy.util

import top.niunaijun.virtugasy.data.AppsRepository
import top.niunaijun.virtugasy.data.FakeLocationRepository
import top.niunaijun.virtugasy.data.GmsRepository
import top.niunaijun.virtugasy.data.XpRepository
import top.niunaijun.virtugasy.view.apps.AppsFactory
import top.niunaijun.virtugasy.view.fake.FakeLocationFactory
import top.niunaijun.virtugasy.view.gms.GmsFactory
import top.niunaijun.virtugasy.view.list.ListFactory
import top.niunaijun.virtugasy.view.xp.XpFactory

/**
 *
 * @Description:
 * @Author: wukaicheng
 * @CreateDate: 2021/4/29 22:38
 */
object InjectionUtil {

    private val appsRepository = AppsRepository()

    private val xpRepository = XpRepository()

    private val gmsRepository = GmsRepository()

    private val fakeLocationRepository = FakeLocationRepository()

    fun getAppsFactory() : AppsFactory {
        return AppsFactory(appsRepository)
    }

    fun getListFactory(): ListFactory {
        return ListFactory(appsRepository)
    }

    fun getXpFactory():XpFactory{
        return XpFactory(xpRepository)
    }

    fun getGmsFactory():GmsFactory{
        return GmsFactory(gmsRepository)
    }

    fun getFakeLocationFactory():FakeLocationFactory{
        return FakeLocationFactory(fakeLocationRepository)
    }
}