fun main() {
    println("Hello, world!!!")

    // 색상 BLUE의 RGB 값을 출력합니다.
    println(Color.BLUE.rgb())
    println(test(Color.RED,Color.BLUE))
    println(joinToString(listOf(1,2,3),";","(",")"))
}

fun test(c1: Color, c2: Color) = when {
    // 두 색상이 RED와 BLUE일 경우 ORANGE를 반환합니다.
    (c1 == Color.RED && c2 == Color.BLUE) -> Color.ORANGE
    else -> throw Exception("TEST")
}

// ORANGE 색상을 enum 클래스에 추가합니다.
enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    ORANGE(255, 165, 0);

    fun rgb() = (r * 256 + g) * 256 + b
}

fun <T> joinToString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {
    val result = StringBuilder(prefix)
    for((idx, value) in collection.withIndex()){
        if(idx > 0) result.append(separator)
        result.append(value)
    }
    result.append(postfix)
    return result.toString()
}
