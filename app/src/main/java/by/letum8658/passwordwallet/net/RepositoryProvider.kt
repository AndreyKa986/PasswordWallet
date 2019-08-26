package by.letum8658.passwordwallet.net

private const val BASE_URL = "https://passwordwallet-914b2.firebaseio.com/Users/"

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