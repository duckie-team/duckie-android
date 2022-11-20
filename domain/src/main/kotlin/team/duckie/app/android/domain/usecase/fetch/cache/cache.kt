package team.duckie.app.android.domain.usecase.fetch.cache

/**
 * 일반적으로 자주 바뀌지 않을 것으로 판단되는 타입들에 대해
 * 캐싱을 진행합니다.
 */
internal enum class CacheType {
    AllFeeds,
    ChatRooms,
    Comments,
    Follow,
    Hearts,
    Review,
    SaleRequest,
    User,
    Notification,
}

private typealias PK = String
private typealias Value = Any

private val caches = HashMap<CacheType, HashMap<PK, Value>>()

/**
 * 값을 캐시에서 불러오거나 새로 요청한 후 캐싱합니다.
 *
 * @param type 캐싱할 타입
 * @param pk 캐싱할 타입의 PK
 * @param forceUpdate 캐시를 무시하고 새로 요청할지 여부
 * @param invoke 만약 캐시에 없다면 새로 요청할 함수
 *
 * @return 캐시에서 불러왔거나 새로 요청해서 가져온 값
 */
internal inline fun <T> invokeOrLoadCache(
    type: CacheType,
    pk: PK,
    forceUpdate: Boolean = false,
    invoke: () -> T,
): T {
    if (forceUpdate) {
        return invokeAndUpdateCache(
            type = type,
            pk = pk,
            invoke = invoke,
        )
    }
    val value = caches[type]?.let { cache ->
        cache[pk]?.let { value ->
            @Suppress("UNCHECKED_CAST")
            value as T
        }
    }
    return value ?: invokeAndUpdateCache(
        type = type,
        pk = pk,
        invoke = invoke,
    )
}

/**
 * 값을 요청한 후 캐싱합니다.
 *
 * @param type 캐싱할 타입
 * @param pk 캐싱할 타입의 PK
 * @param invoke 새로 요청할 함수
 *
 * @return 새로 요청해서 가져온 값
 */
private inline fun <T> invokeAndUpdateCache(
    type: CacheType,
    pk: PK,
    invoke: () -> T,
) = invoke().also { value ->
    caches[type] = (caches[type] ?: HashMap()).apply {
        this[pk] = value as Value
    }
}
