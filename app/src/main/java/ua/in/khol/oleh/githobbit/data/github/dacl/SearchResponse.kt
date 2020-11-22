package ua.`in`.khol.oleh.githobbit.data.github.dacl

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)
