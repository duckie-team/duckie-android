package team.duckie.app.android.domain.model.util

/**
 * 조건에 맞게 꼭 설정돼야 하는 nullable 필드를 검증합니다.
 *
 * ### Assertion fail 조건
 *
 * 1. [condition] 이 true 인데 [value] 값이 null
 * 2. [condition] 이 false 인데 [value] 값이 null 이 아님
 *
 * @param condition nullable 필드가 설정돼야 하는 조건.
 * @param trueConditionDescription [condition] 이 true 가 되는 조건에 대한 설명.
 * assertion fail 메시지에 표시하기 위해 사용됩니다.
 * @param field 조건에 맞게 꼭 설정돼야 하는 필드명.
 * assertion fail 메시지에 표시하기 위해 사용됩니다.
 * @param value 조건에 맞게 꼭 설정돼야 하는 nullable 필드 값
 */
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

/**
 * 항상 꼭 입력되어야 하는 String 필드 값을 검증합니다.
 *
 * ### Assertion fail 조건
 *
 * 1. [value] 값이 비어있음.
 *   비어있는지 검사는 [String.isBlank] 을 통해 진행합니다.
 *
 * @param field 항상 꼭 입력되어야 하는 필드명.
 * assertion fail 메시지에 표시하기 위해 사용됩니다.
 * @param value 항상 꼭 입력되어야 하는 String 필드 값
 */
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

/**
 * 주어진 필드의 사이즈가 지정된 범위 내에 있는지 검증합니다.
 *
 * ### Assertion fail 조건
 *
 * 1. [value] 의 사이즈가 [min]..[max] 에서 벗어남
 *
 * @param min [value] 의 최소 사이즈
 * @param max [value] 의 최대 사이즈
 * @param field 사이즈를 검증할 [Collection] 필드의 이름.
 * assertion fail 메시지에 표시하기 위해 사용됩니다.
 * @param value 사이즈를 검증할 [Collection] 필드 값
 */
internal fun requireSize(
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    field: String,
    value: Collection<*>,
) {
    require(
        value = value.size >= min,
    ) {
        "$field 의 최소 사이즈는 $min 입니다."
    }
    require(
        value = value.size <= max,
    ) {
        "$field 의 사이즈가 $max 보다 큽니다."
    }
}

/**
 * 주어진 필드의 범위가 지정된 범위 내에 있는지 검증합니다.
 *
 * ### Assertion fail 조건
 *
 * 1. [value] 의 범위가 [min]..[max] 에서 벗어남
 *
 * @param min [value] 의 최소 범위
 * @param max [value] 의 최대 범위
 * @param field 범위를 검증할 [Int] 필드의 이름.
 * assertion fail 메시지에 표시하기 위해 사용됩니다.
 * @param value 범위를 검증할 [Int] 필드 값
 */
internal fun requireRange(
    min: Int = 0,
    max: Int = Int.MAX_VALUE,
    field: String,
    value: Int,
) {
    require(
        value = value >= min,
    ) {
        "$field 의 최소 범위는 $min 입니다."
    }
    require(
        value = value <= max,
    ) {
        "$field 의 범위가 $max 보다 큽니다."
    }
}
