package land.sungbin.androidprojecttemplate.domain.model.util

internal fun requireSetting(
    condition: Boolean,
    trueConditionDescription: String,
    field: String,
    value: Any?,
) {
    if (condition) {
        require(
            value = value != null,
        ) {
            "$trueConditionDescription 일 때는 $field 가 설정돼야 합니다."
        }
    } else {
        require(
            value = value == null,
        ) {
            "$trueConditionDescription 일 때만 $field 가 설정돼야 합니다. " +
                    "$field 의 값을 null 로 설정해 주세요."
        }
    }
}

internal fun requireInput(
    field: String,
    value: String,
) {
    require(
        value = value.isNotBlank(),
    ) {
        "$field 는 비어있을 수 없습니다."
    }
}

internal fun requireSize(
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    field: String,
    value: Collection<*>,
) {
    require(
        value = value.size >= min,
    ) {
        "$field 의 최소 크기는 $min 입니다."
    }
    require(
        value = value.size <= max,
    ) {
        "$field 의 크기가 $max 보다 큽니다."
    }
}

internal fun requireSize(
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    field: String,
    value: Int,
) {
    require(
        value = value >= min,
    ) {
        "$field 의 최소 크기는 $min 입니다."
    }
    require(
        value = value <= max,
    ) {
        "$field 의 크기가 $max 보다 큽니다."
    }
}
