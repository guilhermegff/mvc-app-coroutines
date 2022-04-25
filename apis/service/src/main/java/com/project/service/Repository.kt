package com.project.service

class Repository(private val dataSource: DataSource) : DataSource {

    companion object {
        private var INSTANCE: Repository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param remoteDataSource the backend data source
         * *
         * @return the [RemoteRepository] instance
         */
        @JvmStatic
        fun getInstance(dataSource: DataSource) = INSTANCE
            ?: synchronized(Repository::class.java) {
                INSTANCE
                    ?: Repository(dataSource)
                        .also { INSTANCE = it }
            }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override suspend fun locations() = dataSource.locations()
    override suspend fun establishment(id: String) = dataSource.establishment(id)
}