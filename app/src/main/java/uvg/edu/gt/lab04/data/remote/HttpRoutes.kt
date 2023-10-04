package uvg.edu.gt.lab04.data.remote

object HttpRoutes {
    private const val BASE_URL = "https://api.imgflip.com"
    const val GET_MEMES = "${BASE_URL}/get_memes"
    const val CAPTION_IMAGE = "${BASE_URL}/caption_image"
}