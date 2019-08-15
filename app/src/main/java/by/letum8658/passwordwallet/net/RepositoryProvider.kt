package by.letum8658.passwordwallet.net

private const val BASE_URL = " "

fun provideItemRepository(): ItemRepository {
    return ItemRepositoryRemote(
        NetProvider.provideItemApi(
            NetProvider.provideRetrofit(
                BASE_URL,
                NetProvider.provideOkHttp(),
                NetProvider.provideGson()
            )
        )
    )
}