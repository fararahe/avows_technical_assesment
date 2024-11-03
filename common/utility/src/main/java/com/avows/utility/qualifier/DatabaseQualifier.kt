package com.avows.utility.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CartDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BillDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ListProductDaoQualifier