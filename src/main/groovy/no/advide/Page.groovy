package no.advide

class Page {
  String name
  File file
  List<String> lines

  Page(String name, File file) {
    this.name = name
    this.file = file
    this.lines = file.readLines('UTF-8')
  }
}
