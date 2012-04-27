/*
 * テキストファイルを読み込んで、予め指定した文字数ごとにカンマを入れて
 * out.txtに吐く。
 *
 * テキストファイルを引数に受ける。
 * 何文字ごとにコンマを入れるか指定する。
 * 区切り位置は別ファイルで指定
 *
 * */

import scala.io.Source
import java.io.PrintWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object Main {

  def main(args: Array[String]): Unit = {

    val startTime = System.currentTimeMillis()
    // 引数チェック
    try{
      argsCheck(args)

    }catch{
      case e: IllegalArgumentException => println(e.getMessage())
      sys.exit()
    }

    addComma(args(0))

    println(System.currentTimeMillis() - startTime + "msec")
  }

  // ToDo
  // -helpとかどうすんの？
  def argsCheck(args: Array[String]) = {
    require(args.length > 0, "引数の数が不正です。")
    if(args(0).contentEquals("-help")){
      println("TODO：helpを表示")
      sys.exit()
    }
    require(args(0).endsWith(".txt"), "一つ目の引数には .txtファイルを指定してください。")

    // sys.exit()
  }

  /*
   * テキストファイル一行読み込む。
   * 指定位置にカンマを入れる。
   * 別テキストに吐き出す。
   * */
  def addComma(txtname: String) = {
    // テキストファイルの読み込み
    val source = Source.fromFile(txtname)
    // どこで区切るかを指定したファイルの読み込み
    val comma_pos = Source.fromFile("comma_pos.txt")
    val positions = comma_pos.mkString.split(",")

    // 1行ずつ読み込み
    source.getLines().foreach(line => {
      var j = 0
      
      val a = for(pos <- positions) yield {
        val temp = line.subSequence(j, pos.toInt + j) + ","
        j = j + pos.toInt
        temp.mkString
      }
      
      // 最後のコンマを削除
      val b = a.mkString.dropRight(1)

      println(b)
      // 書き出し
      mkFile(b)
    })

  }
  
  def mkFile(line: String):Unit = {
    val out = new FileOutputStream("out.txt", true)
    val writer = new OutputStreamWriter(out)
    writer.write(line + "\r\n")
    writer.close()
  }
  
}
