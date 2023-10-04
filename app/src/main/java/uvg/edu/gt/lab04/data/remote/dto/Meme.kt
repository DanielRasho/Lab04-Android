package uvg.edu.gt.lab04.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class Meme (
    var id : String,
    var name : String,
    var url : String,
    var width : Int,
    var height: Int,
    var box_count: Int,
    var captions: Int
)