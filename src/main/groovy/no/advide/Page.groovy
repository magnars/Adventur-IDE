package no.advide

class Page {
  String name
  File file
  Document document

  Page(String name, File file) {
    this.name = name
    this.file = file
    this.document = new Document(file.readLines('UTF-8'), [x: 0, y: 0])
  }
}
