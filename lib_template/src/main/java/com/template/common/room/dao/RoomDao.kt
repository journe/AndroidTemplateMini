package com.template.common.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.template.common.room.bean.RoomBean
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao : BaseDao<RoomBean> {
	@Query("SELECT * FROM RoomBean WHERE id = :id LIMIT 1")
	fun getUserById(id: Int): RoomBean?

	@Query("SELECT * FROM RoomBean WHERE id = :id LIMIT 1")
	fun getUserByIdFlow(id: Int): Flow<RoomBean?>

	@Query("SELECT * FROM RoomBean WHERE rowid = :rowId")
	fun getUserByRowId(rowId: Long): RoomBean?

	@Query("SELECT * FROM RoomBean")
	fun getAllUsers(): Flow<List<RoomBean>?>
}