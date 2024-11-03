package com.avows.utility.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProfileApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoriesApiQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductApiQualifier