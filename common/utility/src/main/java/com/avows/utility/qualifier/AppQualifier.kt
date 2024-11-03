package com.avows.utility.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkhttpQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrefDataStoreQualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class PrefDataStoreManagerQualifier