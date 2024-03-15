//[app](../../../index.md)/[com.example.filmosis.adapters](../index.md)/[ListsAdapter](index.md)

# ListsAdapter

[androidJvm]\
class [ListsAdapter](index.md)(lists: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)&gt;, onListClick: ([ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onDeleteClick: ([ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[ListsAdapter.ListViewHolder](-list-view-holder/index.md)&gt; 

Adaptador para mostrar elementos de listas en un RecyclerView.

## Constructors

| | |
|---|---|
| [ListsAdapter](-lists-adapter.md) | [androidJvm]<br>constructor(lists: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)&gt;, onListClick: ([ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onDeleteClick: ([ListItem](../../com.example.filmosis.dataclass/-list-item/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |

## Types

| Name | Summary |
|---|---|
| [ListViewHolder](-list-view-holder/index.md) | [androidJvm]<br>inner class [ListViewHolder](-list-view-holder/index.md)(itemView: [View](https://developer.android.com/reference/kotlin/android/view/View.html)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)<br>ViewHolder para cada fila de lista en el RecyclerView. |

## Functions

| Name | Summary |
|---|---|
| [bindViewHolder](index.md#-902019830%2FFunctions%2F-912451524) | [androidJvm]<br>fun [bindViewHolder](index.md#-902019830%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [createViewHolder](../-servicio-adapter/index.md#1423244545%2FFunctions%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)<br>fun [createViewHolder](../-servicio-adapter/index.md#1423244545%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ListsAdapter.ListViewHolder](-list-view-holder/index.md) |
| [deleteItemByListId](delete-item-by-list-id.md) | [androidJvm]<br>fun [deleteItemByListId](delete-item-by-list-id.md)(listId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina un elemento de la lista mutable por su identificador de lista y notifica al adaptador sobre el cambio. |
| [findRelativeAdapterPositionIn](../-servicio-adapter/index.md#-1238180073%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [findRelativeAdapterPositionIn](../-servicio-adapter/index.md#-1238180073%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)&gt;, @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html), p2: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getItemCount](get-item-count.md) | [androidJvm]<br>open override fun [getItemCount](get-item-count.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Devuelve el número total de elementos en la lista mutable de listas. |
| [getItemId](../-servicio-adapter/index.md#725914875%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getItemId](../-servicio-adapter/index.md#725914875%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getItemViewType](../-personas-adapter/index.md#714126295%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getItemViewType](../-personas-adapter/index.md#714126295%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getStateRestorationPolicy](../-servicio-adapter/index.md#1717359980%2FFunctions%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)<br>fun [getStateRestorationPolicy](../-servicio-adapter/index.md#1717359980%2FFunctions%2F-912451524)(): [RecyclerView.Adapter.StateRestorationPolicy](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.StateRestorationPolicy.html) |
| [hasObservers](../-servicio-adapter/index.md#1092162006%2FFunctions%2F-912451524) | [androidJvm]<br>fun [hasObservers](../-servicio-adapter/index.md#1092162006%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasStableIds](../-servicio-adapter/index.md#16685238%2FFunctions%2F-912451524) | [androidJvm]<br>fun [hasStableIds](../-servicio-adapter/index.md#16685238%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [notifyDataSetChanged](../-servicio-adapter/index.md#-1095556076%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyDataSetChanged](../-servicio-adapter/index.md#-1095556076%2FFunctions%2F-912451524)() |
| [notifyItemChanged](../-servicio-adapter/index.md#-1721030169%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemChanged](../-servicio-adapter/index.md#-1721030169%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>fun [notifyItemChanged](../-servicio-adapter/index.md#748267402%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)p1: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [notifyItemInserted](../-servicio-adapter/index.md#2137269507%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemInserted](../-servicio-adapter/index.md#2137269507%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemMoved](../-servicio-adapter/index.md#-1694317867%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemMoved](../-servicio-adapter/index.md#-1694317867%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRangeChanged](../-servicio-adapter/index.md#1769183193%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemRangeChanged](../-servicio-adapter/index.md#1769183193%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>fun [notifyItemRangeChanged](../-servicio-adapter/index.md#1916975740%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)p2: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [notifyItemRangeInserted](../-servicio-adapter/index.md#-2104748521%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemRangeInserted](../-servicio-adapter/index.md#-2104748521%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRangeRemoved](../-servicio-adapter/index.md#999899269%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemRangeRemoved](../-servicio-adapter/index.md#999899269%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [notifyItemRemoved](../-servicio-adapter/index.md#-189254469%2FFunctions%2F-912451524) | [androidJvm]<br>fun [notifyItemRemoved](../-servicio-adapter/index.md#-189254469%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [onAttachedToRecyclerView](../-servicio-adapter/index.md#-1243461790%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onAttachedToRecyclerView](../-servicio-adapter/index.md#-1243461790%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onBindViewHolder](index.md#-1236223053%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onBindViewHolder](index.md#-1236223053%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p2: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;)<br>[androidJvm]<br>open override fun [onBindViewHolder](on-bind-view-holder.md)(holder: [ListsAdapter.ListViewHolder](-list-view-holder/index.md), position: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Vincula los datos de una lista específica al ViewHolder en una posición determinada. |
| [onCreateViewHolder](on-create-view-holder.md) | [androidJvm]<br>open override fun [onCreateViewHolder](on-create-view-holder.md)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), viewType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ListsAdapter.ListViewHolder](-list-view-holder/index.md)<br>Crea un nuevo ViewHolder para las filas de lista. |
| [onDetachedFromRecyclerView](../-servicio-adapter/index.md#-1201433889%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onDetachedFromRecyclerView](../-servicio-adapter/index.md#-1201433889%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.html)) |
| [onFailedToRecycleView](index.md#-1984965282%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onFailedToRecycleView](index.md#-1984965282%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onViewAttachedToWindow](index.md#1580998584%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onViewAttachedToWindow](index.md#1580998584%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md)) |
| [onViewDetachedFromWindow](index.md#-1077934667%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onViewDetachedFromWindow](index.md#-1077934667%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md)) |
| [onViewRecycled](index.md#834979674%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onViewRecycled](index.md#834979674%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [ListsAdapter.ListViewHolder](-list-view-holder/index.md)) |
| [registerAdapterDataObserver](../-servicio-adapter/index.md#-149943229%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [registerAdapterDataObserver](../-servicio-adapter/index.md#-149943229%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.AdapterDataObserver](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.AdapterDataObserver.html)) |
| [setHasStableIds](../-servicio-adapter/index.md#1991189249%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setHasStableIds](../-servicio-adapter/index.md#1991189249%2FFunctions%2F-912451524)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setStateRestorationPolicy](../-servicio-adapter/index.md#1439711293%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setStateRestorationPolicy](../-servicio-adapter/index.md#1439711293%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.Adapter.StateRestorationPolicy](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.StateRestorationPolicy.html)) |
| [unregisterAdapterDataObserver](../-servicio-adapter/index.md#607934410%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [unregisterAdapterDataObserver](../-servicio-adapter/index.md#607934410%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [RecyclerView.AdapterDataObserver](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.AdapterDataObserver.html)) |