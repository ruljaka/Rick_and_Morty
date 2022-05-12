//package com.ruslangrigoriev.rickandmorty.data.paging
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
//import com.ruslangrigoriev.rickandmorty.data.entity.CharacterRemoteKey
//import com.ruslangrigoriev.rickandmorty.data.local.AppDataBase
//import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
//import retrofit2.HttpException
//import java.io.IOException
//
//
//const val STARTING_PAGE_INDEX = 1
//
//
//@ExperimentalPagingApi
//class СharacterRemoteMediator(
//    private val name: String? = null,
//    private val status: String? = null,
//    private val species: String? = null,
//    private val type: String? = null,
//    private val gender: String? = null,
//    private val api: CharactersService,
//    private val db: AppDataBase
//) : RemoteMediator<Int, CharacterDTO>() {
//
////    override suspend fun initialize(): InitializeAction {
////        return InitializeAction.LAUNCH_INITIAL_REFRESH
////    }
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, CharacterDTO>
//    ): MediatorResult {
//        val pageKeyData = getKeyPageData(loadType, state)
//        val page = when (pageKeyData) {
//            is MediatorResult.Success -> {
//                return pageKeyData
//            }
//            else -> {
//                pageKeyData as Int
//            }
//        }
//
//        try {
//            val response = api.getCharacters(
//                page = page,
//                name = name,
//                status = status,
//                species = species,
//                type = type,
//                gender = gender
//            ).body()
//            val data = response?.characters ?: emptyList()
//            val isEndOfList = data.isEmpty()
//            db.withTransaction {
////                if (loadType == LoadType.REFRESH) {
////                    db.getCharactersDao().deleteAll()
////                    db.getCharacterRemoteKeyDao().deleteAll()
////                }
//                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
//                val nextKey = if (isEndOfList) null else page + 1
//                val keys = data.map {
//                    CharacterRemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                db.getCharactersDao().insertCharacters(data)
//                db.getCharacterRemoteKeyDao().insertAll(keys)
//            }
//            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
//        } catch (exception: IOException) {
//            return MediatorResult.Error(exception)
//        } catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
//    private suspend fun getKeyPageData(
//        loadType: LoadType,
//        state: PagingState<Int, CharacterDTO>
//    ): Any {
//        return when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getLastRemoteKey(state)
//                val nextKey = remoteKeys?.nextKey
//                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
//            }
//            LoadType.PREPEND -> {
//                return MediatorResult.Success(true)
//            }
//        }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterDTO>): CharacterRemoteKey? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
//                db.getCharacterRemoteKeyDao().getRemoteKeyById(repoId)
//            }
//        }
//    }
//
//    private suspend fun getLastRemoteKey(state: PagingState<Int, CharacterDTO>): CharacterRemoteKey? {
//        return state.lastItemOrNull()
//            ?.let { cat -> db.getCharacterRemoteKeyDao().getRemoteKeyById(cat.id) }
//    }
//
//    private suspend fun getFirstRemoteKey(state: PagingState<Int, CharacterDTO>): CharacterRemoteKey? {
//        return state.firstItemOrNull()
//            ?.let { cat -> db.getCharacterRemoteKeyDao().getRemoteKeyById(cat.id) }
//    }
//
//
//}